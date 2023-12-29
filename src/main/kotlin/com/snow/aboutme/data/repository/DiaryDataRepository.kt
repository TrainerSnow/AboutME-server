package com.snow.aboutme.data.repository

import com.snow.aboutme.data.model.day.DiaryDataEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DiaryDataRepository: CrudRepository<DiaryDataEntity, Long>