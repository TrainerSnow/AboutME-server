package com.snow.aboutme.data.repository

import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.day.MoodDataEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface MoodDataRepository: CrudRepository<MoodDataEntity, Long> {

    fun findByDateAndUser(date: LocalDate, user: User): MoodDataEntity?

    fun findAllByUser(user: User): List<MoodDataEntity>

}