package com.snow.aboutme.data.repository

import com.snow.aboutme.data.model.RelationEntity
import com.snow.aboutme.data.model.User
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface RelationRepository: CrudRepository<RelationEntity, Long> {

    fun findByUserAndId(user: User, id: Long): RelationEntity?

    fun findAllByUser(user: User): List<RelationEntity>

}