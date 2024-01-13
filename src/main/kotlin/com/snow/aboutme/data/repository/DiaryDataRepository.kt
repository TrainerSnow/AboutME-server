package com.snow.aboutme.data.repository

import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.day.DiaryDataEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
interface DiaryDataRepository: CrudRepository<DiaryDataEntity, Long> {

    fun findByDateAndUser(date: LocalDate, user: User): DiaryDataEntity?
    fun findAllByUser(user: User): List<DiaryDataEntity>

}