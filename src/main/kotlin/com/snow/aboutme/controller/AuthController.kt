package com.snow.aboutme.controller;

import com.snow.aboutme.annotation.GraphQLAnonymous
import com.snow.aboutme.auth.JwtService
import com.snow.aboutme.auth.RefreshService
import com.snow.aboutme.controller.model.AuthData
import com.snow.aboutme.controller.model.AuthUser
import com.snow.aboutme.data.graphql.NameInfoInput
import com.snow.aboutme.data.model.NameInfo
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.repository.NameInfoRepository
import com.snow.aboutme.data.repository.RefreshTokenRepository
import com.snow.aboutme.data.repository.UserRepository
import com.snow.aboutme.exception.AboutMeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Controller
import java.time.Instant

@Controller
class AuthController {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var nameInfoRepository: NameInfoRepository

    @Autowired
    lateinit var refreshService: RefreshService

    @Autowired
    lateinit var jwtService: JwtService

    @Autowired
    lateinit var refreshTokenRepository: RefreshTokenRepository

    @Autowired
    lateinit var passwordEncoder: PasswordEncoder

    @MutationMapping
    @GraphQLAnonymous
    fun signUp(
        @Argument nameInfo: NameInfoInput,
        @Argument email: String,
        @Argument password: String
    ): AuthUser {
        userRepository.validateInput(email)

        val userName = nameInfoRepository.save(
            NameInfo(
                nameInfo.firstName,
                nameInfo.middleName,
                nameInfo.lastName,
                nameInfo.title
            )
        )
        val user = User(
            email = email,
            passwordHash = passwordEncoder.encode(password),
            refreshToken = null,
            persons = emptySet(),
            personRelations = emptySet(),
            dayData = emptySet(),
            nameInfo = userName
        ).let(userRepository::save)

        val jwt = jwtService.createForUser(user)
        val refresh = refreshService.createForUser(user)

        user.refreshToken = refresh
        userRepository.save(user)

        return AuthUser(
            user = user,
            authData = AuthData(jwt, refresh.id.toString())
        )
    }

    @MutationMapping
    @GraphQLAnonymous
    fun refresh(
        @Argument refreshToken: String
    ): AuthUser {
        val user = refreshService.userFor(refreshToken) ?: throw AboutMeException.AuthException.MalformedAuthException()
        val refresh = user.refreshToken!!

        if (refresh.expirationDate.isBefore(Instant.now())) throw AboutMeException.AuthException.ExpiredAuthException()

        val newRefresh = refreshService.createForUser(user)
        val newToken = jwtService.createForUser(user)

        return AuthUser(
            user = user,
            authData = AuthData(
                refreshToken = newRefresh.id.toString(),
                token = newToken
            )
        )
    }

    @MutationMapping
    @GraphQLAnonymous
    fun login(
        @Argument email: String,
        @Argument password: String
    ): AuthUser {
        val user = userRepository.findByEmail(email)
            .orElseThrow { AboutMeException.AuthException.InvalidCredentialsException() }

        val pwMatches = passwordEncoder.matches(password, user.passwordHash)
        if (!pwMatches) throw AboutMeException.AuthException.InvalidCredentialsException()

        val refreshToken = refreshService.createForUser(user)
        val token = jwtService.createForUser(user)

        return AuthUser(
            user = user,
            authData = AuthData(
                token = token,
                refreshToken = refreshToken.id.toString()
            )
        )
    }

}

private fun UserRepository.validateInput(email: String) {
    if (existsByEmail(email)) throw AboutMeException.Conflict(email)
}