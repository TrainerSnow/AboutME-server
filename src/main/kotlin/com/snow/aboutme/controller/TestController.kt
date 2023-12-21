package com.snow.aboutme.controller;

import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.repository.NameInfoRepository
import com.snow.aboutme.data.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Controller

@Controller
class TestController {

    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var nameInfoRepository: NameInfoRepository

    @QueryMapping
    @PreAuthorize("isAuthenticated()")
    fun allUsers(): List<User> {
        return userRepository.findAll().toList()
    }

}