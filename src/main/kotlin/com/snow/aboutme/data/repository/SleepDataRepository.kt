package com.snow.aboutme.data.repository

import com.snow.aboutme.data.model.day.SleepDataEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface SleepDataRepository: CrudRepository<SleepDataEntity, Long>