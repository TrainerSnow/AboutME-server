package com.snow.aboutme.auth;

import com.snow.aboutme.data.model.RefreshToken
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.repository.RefreshTokenRepository
import com.snow.aboutme.data.repository.UserRepository
import com.snow.aboutme.data.repository.findByRefreshToken
import com.snow.aboutme.util.toUUID
import jakarta.transaction.Transactional
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.Instant

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
        //Delete all expired tokens
        refreshRepository.deleteByUserAndExpirationDateBefore(user, Instant.now())

        val refreshToken = RefreshToken(
            expirationDate = Instant.now().plusSeconds(expirationSeconds.toLong()),
            user = user
        )
        user.refreshTokens.add(refreshToken)
        userRepository.save(user)
        return refreshRepository.save(refreshToken)
    }

    fun userFor(refreshToken: String): User? = refreshToken
        .toUUID()
        ?.let(refreshRepository::findById)?.orElse(null)
        ?.let(userRepository::findByRefreshToken)
        ?.orElse(null)

    fun deleteAllForUser(user: User) {
        user.refreshTokens = mutableSetOf()
        userRepository.save(user)
        refreshRepository.deleteByUser(user)
    }


}