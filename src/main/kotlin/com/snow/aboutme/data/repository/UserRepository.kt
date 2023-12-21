package com.snow.aboutme.data.repository;

import com.snow.aboutme.data.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional
import java.util.UUID

@Repository
interface UserRepository: CrudRepository<User, Long> {

    fun findByEmail(email: String): Optional<User>

    fun findByRefreshToken_Id(id: UUID): Optional<User>

    fun existsByEmail(email: String): Boolean

}