package com.snow.aboutme.data.repository

import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.day.DreamDataEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface DreamDataRepository: CrudRepository<DreamDataEntity, Long> {

    fun findByDateAndUser(date: LocalDate, user: User): DreamDataEntity?

    fun findAllByUser(user: User): List<DreamDataEntity>

}