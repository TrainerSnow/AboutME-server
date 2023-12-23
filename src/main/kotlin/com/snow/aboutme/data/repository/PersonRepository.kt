package com.snow.aboutme.data.repository

import com.snow.aboutme.data.model.Person
import com.snow.aboutme.data.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface PersonRepository: CrudRepository<Person, Long> {

    fun findByUserAndId(user: User, id: Long): Optional<Person>

}