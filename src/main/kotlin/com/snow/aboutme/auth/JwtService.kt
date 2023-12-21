package com.snow.aboutme.auth;

import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.repository.UserRepository
import com.snow.aboutme.exception.AboutMeException
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
class JwtService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Value("\${auth.jwt.expiration}")
    private lateinit var expiration: String

    @Value("\${auth.jwt.secret}")
    private lateinit var secret: String

    fun createForUser(user: User): String {
        val claims = user.createClaims()

        val key = Keys.hmacShaKeyFor(secret.toByteArray())

        return Jwts
            .builder()
            .setClaims(claims)
            .setExpiration(Date.from(Instant.now().plusSeconds(expiration.toLong())))
            .signWith(key)
            .compact()
    }

    fun getTokenFromHeader(req: HttpServletRequest): String? {
        val header: String? = req.getHeader(AuthHeader)
        return header?.getTokenFromHeader()
    }

    fun getClaimsFromToken(token: String): Claims? {
        return try {
            Jwts
                .parserBuilder()
                .setSigningKey(secret.toByteArray())
                .build()
                .parseClaimsJws(token)
                .body
        } catch (e: Exception) {
            null
        }
    }

    fun validate(req: HttpServletRequest): User? {
        val token = getTokenFromHeader(req) ?: return null
        val claims = getClaimsFromToken(token)?.ensureEmail() ?: return null

        val email = claims.subject
        val user = userRepository.findByEmail(email)
        return if (user.isPresent) {
            user.get()
        } else {
            null
        }
    }

    private fun String.getTokenFromHeader(): String? = if (startsWith(Bearer))
        removePrefix(Bearer)
    else
        null

    private fun Claims.ensureEmail(): Claims? {
        return if (this.subject == null) null
        else this
    }

    private fun User.createClaims(): Claims = Jwts
        .claims().apply {
            expiration = Date.from(Instant.now().plusSeconds(this@JwtService.expiration.toLong()))
            issuedAt = Date.from(Instant.now())
            subject = email
            set("firstName", nameInfo.firstName)
        }

    companion object {

        const val AuthHeader = "Authorization"

        const val Bearer = "Bearer: "

    }

}