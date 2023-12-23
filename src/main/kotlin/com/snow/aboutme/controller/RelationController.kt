package com.snow.aboutme.controller;

import com.snow.aboutme.annotation.GraphQLAuthenticated
import com.snow.aboutme.controller.model.PersonRelationInput
import com.snow.aboutme.data.model.PersonRelation
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.repository.RelationRepository
import com.snow.aboutme.data.repository.UserRepository
import com.snow.aboutme.exception.AboutMeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller

@Controller
class RelationController {

    @Autowired
    private lateinit var relationRepository: RelationRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @GraphQLAuthenticated
    @MutationMapping
    fun addRelation(
        @Argument personRelationInput: PersonRelationInput,
        @AuthenticationPrincipal user: User
    ): PersonRelation {
        val relation = PersonRelation(
            name = personRelationInput.name,
            color = personRelationInput.color,
            persons = mutableSetOf(),
            user = user
        ).let(relationRepository::save)

        user.personRelations.add(relation)

        userRepository.save(user)
        relationRepository.save(relation)

        return relation
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun deleteRelation(
        @Argument id: Long,
        @AuthenticationPrincipal user: User
    ): PersonRelation {
        val relation =
            relationRepository.findByUserAndId(user, id).orElseThrow { AboutMeException.NotFoundException(id) }

        user.personRelations.removeIf { it.id == id }

        userRepository.save(user)
        relationRepository.delete(relation)

        return relation
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun updateRelation(
        @Argument id: Long,
        @Argument relationInput: PersonRelationInput,
        @AuthenticationPrincipal user: User
    ): PersonRelation {
        val relation =
            relationRepository.findByUserAndId(user, id).orElseThrow { AboutMeException.NotFoundException(id) }

        relation.name = relationInput.name
        relation.color = relationInput.color

        relationRepository.save(relation)

        return relation
    }

}