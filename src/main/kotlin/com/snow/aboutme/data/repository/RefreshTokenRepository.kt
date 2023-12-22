package com.snow.aboutme.data.repository

import com.snow.aboutme.data.model.RefreshToken
import com.snow.aboutme.data.model.User
import org.springframework.data.repository.CrudRepository
import java.time.Instant
import java.util.Optional
import java.util.UUID

interface RefreshTokenRepository : CrudRepository<RefreshToken, UUID> {

    fun deleteByUserAndExpirationDateBefore(user: User, expirationDate: Instant)

}