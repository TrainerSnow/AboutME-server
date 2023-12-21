package com.snow.aboutme.auth;

import com.snow.aboutme.data.model.RefreshToken
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.repository.RefreshTokenRepository
import com.snow.aboutme.data.repository.UserRepository
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant
import java.util.*

@Service
@Transactional
class RefreshService {

    @Autowired
    private lateinit var refreshRepository: RefreshTokenRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @Value("\${auth.refresh.expiration}")
    private lateinit var expirationSeconds: String

    fun createForUser(user: User): RefreshToken {
        user.refreshToken?.id?.let(refreshRepository::deleteById)

        val refreshToken = RefreshToken(
            expirationDate = Instant.now().plusSeconds(expirationSeconds.toLong())
        )
        user.refreshToken = refreshToken
        userRepository.save(user)
        return refreshRepository.save(refreshToken)
    }

    fun userFor(refreshToken: String): User? = userRepository
        .findByRefreshToken_Id(UUID.fromString(refreshToken))
        .orElse(null)


}