package com.snow.aboutme.data.repository

import com.snow.aboutme.data.model.DayDataEntity
import com.snow.aboutme.data.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.util.Optional

@Repository
interface DayDataRepository: CrudRepository<DayDataEntity, Long> {

    fun findByUserAndDate(user: User, date: LocalDate): Optional<DayDataEntity>

}