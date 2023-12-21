package com.snow.aboutme.data.repository

import com.snow.aboutme.data.model.NameInfo
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NameInfoRepository : CrudRepository<NameInfo, Long>