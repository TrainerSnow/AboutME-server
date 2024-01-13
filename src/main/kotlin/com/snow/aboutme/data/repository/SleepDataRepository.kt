package com.snow.aboutme.data.repository

import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.day.SleepDataEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface SleepDataRepository : CrudRepository<SleepDataEntity, Long> {

    fun findByDateAndUser(date: LocalDate, user: User): SleepDataEntity?

    fun findAllByUser(user: User): List<SleepDataEntity>

}