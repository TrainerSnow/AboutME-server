package com.snow.aboutme.controller;

import com.snow.aboutme.annotation.GraphQLAuthenticated
import com.snow.aboutme.controller.model.PersonInput
import com.snow.aboutme.data.model.NameInfo
import com.snow.aboutme.data.model.Person
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.update
import com.snow.aboutme.data.repository.NameInfoRepository
import com.snow.aboutme.data.repository.PersonRepository
import com.snow.aboutme.data.repository.RelationRepository
import com.snow.aboutme.data.repository.UserRepository
import com.snow.aboutme.exception.AboutMeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller

@Controller
class PersonController {

    @Autowired
    private lateinit var personRepository: PersonRepository

    @Autowired
    private lateinit var relationRepository: RelationRepository

    @Autowired
    private lateinit var nameInfoRepository: NameInfoRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @GraphQLAuthenticated
    @MutationMapping
    fun addPerson(
        @Argument personInput: PersonInput,
        @AuthenticationPrincipal user: User
    ): Person {
        val relation = relationRepository.findByUserAndId(user, personInput.personRelationId)
            .orElseThrow { AboutMeException.NotFoundException(personInput.personRelationId) }

        val nameInfo = NameInfo(
            personInput.nameInfoInput.firstName,
            personInput.nameInfoInput.middleName,
            personInput.nameInfoInput.lastName,
            personInput.nameInfoInput.title
        ).let(nameInfoRepository::save)

        val person = Person(
            nameInfo = nameInfo,
            relation = relation,
            user = user,
            dreams = mutableSetOf()
        )

        user.persons.add(person)

        userRepository.save(user)
        personRepository.save(person)

        return person
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun deletePerson(
        @Argument id: Long,
        @AuthenticationPrincipal user: User
    ): Person {
        val person = personRepository.findByUserAndId(user, id).orElseThrow { AboutMeException.NotFoundException(id) }

        user.persons.removeIf { it.id == id }

        userRepository.save(user)
        personRepository.delete(person)

        return person
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun updatePerson(
        @Argument id: Long,
        @Argument personInput: PersonInput,
        @AuthenticationPrincipal user: User
    ): Person {
        val person = personRepository.findByUserAndId(user, id).orElseThrow { AboutMeException.NotFoundException(id) }

        val nameInfo = person.nameInfo.update(
            firstName = personInput.nameInfoInput.firstName,
            middleName = personInput.nameInfoInput.middleName,
            lastName = personInput.nameInfoInput.lastName,
            title = personInput.nameInfoInput.title
        ).let(nameInfoRepository::save)

        person.nameInfo = nameInfo

        val relation = relationRepository.findByUserAndId(user, personInput.personRelationId)
            .orElseThrow { AboutMeException.NotFoundException(personInput.personRelationId) }

        val oldRelation = person.relation

        relation.persons.add(person)
        oldRelation.persons.removeIf { it.id == person.id }
        person.relation = relation

        relationRepository.save(relation)
        relationRepository.save(oldRelation)
        personRepository.save(person)

        return person
    }

}