package com.snow.aboutme.data.repository

import com.snow.aboutme.data.model.day.DreamDataEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DreamDataRepository: CrudRepository<DreamDataEntity, Long>