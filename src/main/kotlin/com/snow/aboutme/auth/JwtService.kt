package com.snow.aboutme.auth;

import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.repository.UserRepository
import com.snow.aboutme.exception.AboutMeException
import io.jsonwebtoken.*
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*
import javax.crypto.spec.SecretKeySpec

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

        return Jwts
            .builder()
            .claims(claims)
            .expiration(Date.from(Instant.now().plusSeconds(expiration.toLong())))
            .signWith(SignatureAlgorithm.HS512, secret.toByteArray())
            .compact()
    }

    fun getTokenFromHeader(req: HttpServletRequest): String? {
        val header = req.getHeader(AuthHeader)
        return header.getTokenFromHeader()
    }

    fun getClaimsFromToken(token: String): Claims {
        return try {
            Jwts
                .parser()
                .verifyWith(SecretKeySpec(secret.toByteArray(), "AES"))
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (e: MalformedJwtException) {
            throw AboutMeException.AuthException.MalformedAuthException()
        } catch (e: ExpiredJwtException) {
            throw AboutMeException.AuthException.ExpiredAuthException()
        } catch (e: JwtException) {
            throw AboutMeException.AuthException.NoAuthException()
        }
    }

    fun validate(req: HttpServletRequest): User {
        val token = getTokenFromHeader(req) ?: throw AboutMeException.AuthException.NoAuthException()
        val claims = getClaimsFromToken(token).ensureEmail()

        val email = claims.subject
        val user = userRepository.findByEmail(email)
        if (user.isPresent) {
            return user.get()
        } else {
            throw AboutMeException.AuthException.InsufficientAuthException()
        }
    }

    private fun String.getTokenFromHeader(): String? = if (startsWith(Bearer))
        removePrefix(Bearer)
    else
        null

    private fun Claims.ensureEmail(): Claims {
        if (this.subject == null) throw AboutMeException.AuthException.InsufficientAuthException()
        return this
    }

    private fun User.createClaims(): Claims = Jwts
        .claims()
        .expiration(Date.from(Instant.now().plusSeconds(expiration.toLong())))
        .issuedAt(Date.from(Instant.now()))
        .subject(email)
        .add("firstName", nameInfo.firstName)
        .build()

    companion object {

        const val AuthHeader = "Authorization"

        const val Bearer = "Bearer: "

    }

}