package com.snow.aboutme.data.repository

import com.snow.aboutme.data.model.day.MoodDataEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface MoodDataRepository: CrudRepository<MoodDataEntity, Long>