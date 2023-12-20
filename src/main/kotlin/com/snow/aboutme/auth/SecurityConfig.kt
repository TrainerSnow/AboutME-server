package com.snow.aboutme.auth;

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain


@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun filterChain(httpSec: HttpSecurity): SecurityFilterChain {
        httpSec
            .authorizeHttpRequests {
                it
                    .anyRequest().permitAll()
            }
            .csrf {
                it.disable()
            }

        return httpSec.build()
    }

}