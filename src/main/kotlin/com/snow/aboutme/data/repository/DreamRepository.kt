package com.snow.aboutme.data.repository

import com.snow.aboutme.data.model.DreamEntity
import com.snow.aboutme.data.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface DreamRepository: CrudRepository<DreamEntity, Long> {

    fun findByIdAndUser(id: Long, user: User): DreamEntity?

    fun findAllByDateAndUser(date: LocalDate, user: User): List<DreamEntity>

    fun findAllByUser(user: User): List<DreamEntity>

}