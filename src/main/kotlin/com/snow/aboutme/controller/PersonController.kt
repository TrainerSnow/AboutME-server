package com.snow.aboutme.controller;

import com.snow.aboutme.annotation.GraphQLAuthenticated
import com.snow.aboutme.controller.model.CreatePersonInput
import com.snow.aboutme.controller.model.UpdatePersonInput
import com.snow.aboutme.data.model.NameInfo
import com.snow.aboutme.data.model.PersonEntity
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.repository.PersonRepository
import com.snow.aboutme.data.repository.RelationRepository
import com.snow.aboutme.exception.AboutMeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller

@Controller
class PersonController {

    @Autowired
    lateinit var personRepository: PersonRepository

    @Autowired
    lateinit var relationRepository: RelationRepository

    @GraphQLAuthenticated
    @MutationMapping
    fun addPerson(
        @AuthenticationPrincipal user: User,
        @Argument personInput: CreatePersonInput
    ): PersonEntity {
        val relation = relationRepository.findByUserAndId(user, personInput.personRelationId)
            ?: throw AboutMeException.NotFoundException(personInput.personRelationId)

        val new = PersonEntity(
            dreams = mutableSetOf(),
            nameInfo = NameInfo(
                personInput.nameInfoInput.firstName,
                personInput.nameInfoInput.middleName,
                personInput.nameInfoInput.lastName,
                personInput.nameInfoInput.lastName
            ),
            relation = relation,
            user = user
        )
        new.updated = personInput.updated
        new.created = personInput.created

        return personRepository.save(new)
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun deletePerson(
        @AuthenticationPrincipal user: User,
        @Argument id: Long
    ): PersonEntity {
        val entity = personRepository.findByUserAndId(user, id) ?: throw AboutMeException.NotFoundException(id)

        personRepository.delete(entity)
        return entity
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun updatePerson(
        @AuthenticationPrincipal user: User,
        @Argument id: Long,
        @Argument personInput: UpdatePersonInput
    ): PersonEntity {
        val existing = personRepository.findByUserAndId(user, id) ?: throw AboutMeException.NotFoundException(id)

        val newRelation = relationRepository.findByUserAndId(user, personInput.personRelationId)
            ?: throw AboutMeException.NotFoundException(personInput.personRelationId)

        existing.nameInfo = NameInfo(
            personInput.nameInfoInput.firstName,
            personInput.nameInfoInput.middleName,
            personInput.nameInfoInput.lastName,
            personInput.nameInfoInput.lastName
        )
        existing.relation = newRelation
        existing.updated = personInput.updated

        return personRepository.save(existing)
    }

    @GraphQLAuthenticated
    @QueryMapping
    fun getPersonById(
        @AuthenticationPrincipal user: User,
        @Argument id: Long
    ): PersonEntity {
        val entity = personRepository.findByUserAndId(user, id) ?: throw AboutMeException.NotFoundException(id)

        return entity
    }

    @GraphQLAuthenticated
    @QueryMapping
    fun getAllPersons(
        @AuthenticationPrincipal user: User
    ): List<PersonEntity> {
        val all = personRepository.findAllByUser(user)

        return all
    }

}