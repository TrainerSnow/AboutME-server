package com.snow.aboutme.data.repository;

import com.snow.aboutme.data.model.RefreshToken
import com.snow.aboutme.data.model.User
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CrudRepository<User, Long> {

    fun findByEmail(email: String): Optional<User>

    fun existsByEmail(email: String): Boolean

}

fun UserRepository.findByRefreshToken(token: RefreshToken): Optional<User> = Optional.ofNullable(
    findAll()
        .find { token in it.refreshTokens }
)