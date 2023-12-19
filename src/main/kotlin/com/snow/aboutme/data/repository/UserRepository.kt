package com.snow.aboutme.data.repository;

import com.snow.aboutme.data.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: CrudRepository<User, Long>