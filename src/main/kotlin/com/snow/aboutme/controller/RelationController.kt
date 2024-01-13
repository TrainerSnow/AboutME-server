package com.snow.aboutme.controller;

import com.snow.aboutme.annotation.GraphQLAuthenticated
import com.snow.aboutme.controller.model.CreatePersonRelationInput
import com.snow.aboutme.controller.model.UpdatePersonRelationInput
import com.snow.aboutme.data.model.RelationEntity
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.repository.RelationRepository
import com.snow.aboutme.exception.AboutMeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller

@Controller
class RelationController {

    @Autowired
    private lateinit var relationRepository: RelationRepository


    @GraphQLAuthenticated
    @MutationMapping
    fun addRelation(
        @AuthenticationPrincipal user: User,
        @Argument personRelationInput: CreatePersonRelationInput
    ): RelationEntity {
        val new = RelationEntity(
            name = personRelationInput.name,
            color = personRelationInput.color,
            persons = mutableSetOf(),
            user = user
        )
        new.updated = personRelationInput.updated
        new.created = personRelationInput.created

        return relationRepository.save(new)
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun deleteRelation(
        @AuthenticationPrincipal user: User,
        @Argument id: Long
    ): RelationEntity {
        val entity = relationRepository.findByUserAndId(user, id) ?: throw AboutMeException.NotFoundException(id)

        relationRepository.delete(entity)
        return entity
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun updateRelation(
        @AuthenticationPrincipal user: User,
        @Argument id: Long,
        @Argument relationInput: UpdatePersonRelationInput
    ): RelationEntity {
        val existing = relationRepository.findByUserAndId(user, id) ?: throw AboutMeException.NotFoundException(id)

        existing.updated = relationInput.updated
        existing.color = relationInput.color
        existing.name = relationInput.name

        relationRepository.save(existing)

        return existing
    }

    @GraphQLAuthenticated
    @QueryMapping
    fun getRelationById(
        @AuthenticationPrincipal user: User,
        @Argument id: Long
    ): RelationEntity {
        val entity = relationRepository.findByUserAndId(user, id) ?: throw AboutMeException.NotFoundException(id)

        return entity
    }

    @GraphQLAuthenticated
    @QueryMapping
    fun getAlLRelations(
        @AuthenticationPrincipal user: User
    ): List<RelationEntity> {
        val all = relationRepository.findAllByUser(user)

        return all
    }

}