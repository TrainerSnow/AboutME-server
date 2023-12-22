package com.snow.aboutme.auth;

import com.snow.aboutme.data.repository.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class AboutMeUserDetailsService : UserDetailsService {

    @Autowired
    lateinit var userRepository: UserRepository

    override fun loadUserByUsername(username: String?): UserDetails {
        userRepository
            .findByEmail(username ?: "")
            .let {
                if (it.isPresent) return it.get()
                else throw UsernameNotFoundException("Could not find user with email '$username'")
            }
    }

}