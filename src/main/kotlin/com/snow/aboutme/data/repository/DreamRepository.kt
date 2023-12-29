package com.snow.aboutme.data.repository

import com.snow.aboutme.data.model.DreamEntity
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface DreamRepository: CrudRepository<DreamEntity, Long>