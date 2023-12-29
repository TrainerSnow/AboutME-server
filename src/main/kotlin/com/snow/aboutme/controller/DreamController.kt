package com.snow.aboutme.controller;

import com.snow.aboutme.annotation.GraphQLAuthenticated
import com.snow.aboutme.controller.model.DreamInput
import com.snow.aboutme.data.model.DreamEntity
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.day.DreamDataEntity
import com.snow.aboutme.data.model.day.createOrUpdate
import com.snow.aboutme.data.repository.DayDataRepository
import com.snow.aboutme.data.repository.DreamDataRepository
import com.snow.aboutme.data.repository.DreamRepository
import com.snow.aboutme.data.repository.PersonRepository
import com.snow.aboutme.exception.AboutMeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import java.time.Instant
import java.time.LocalDate

@Controller
class DreamController {

    @Autowired
    private lateinit var dreamDataRepository: DreamDataRepository

    @Autowired
    private lateinit var dayDataRepository: DayDataRepository

    @Autowired
    private lateinit var dreamRepository: DreamRepository

    @Autowired
    private lateinit var personRepository: PersonRepository


    //Also implementing the update method here is kinda useless, because a dream data is only there to be references from the dreams.
    //But for consistency we leave it here. Most likely will be used in future
    @GraphQLAuthenticated
    @MutationMapping
    fun addDreamData(
        @Argument date: LocalDate,
        @AuthenticationPrincipal user: User
    ): DreamDataEntity {
        val day =
            dayDataRepository.findByUserAndDate(user, date).orElseThrow { AboutMeException.NotFoundException(date) }

        val dreamData = day.dreamData.createOrUpdate()

        day.dreamData = dreamData

        dayDataRepository.save(day)
        dreamDataRepository.save(dreamData)

        return dreamData
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun deleteDreamData(
        @Argument date: LocalDate,
        @AuthenticationPrincipal user: User
    ): DreamDataEntity {
        val day =
            dayDataRepository.findByUserAndDate(user, date).orElseThrow { AboutMeException.NotFoundException(date) }
        val dreamData = day.dreamData ?: throw AboutMeException.NotFoundException(date)

        day.dreamData = null
        dreamDataRepository.delete(dreamData)
        dayDataRepository.save(day)

        return dreamData
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun addDream(
        @Argument dreamInput: DreamInput,
        @Argument date: LocalDate,
        @AuthenticationPrincipal user: User
    ): DreamEntity {
        val day =
            dayDataRepository.findByUserAndDate(user, date).orElseThrow { AboutMeException.NotFoundException(date) }
        val dreamData = day.dreamData ?: throw AboutMeException.NotFoundException(date)

        val persons = personRepository.findAllById(dreamInput.persons ?: emptySet()).toMutableSet()

        val dream = DreamEntity(
            description = dreamInput.description,
            annotation = dreamInput.annotation,
            clearness = dreamInput.clearness,
            mood = dreamInput.mood,
            persons = persons,
            dreamData = dreamData
        ).let(dreamRepository::save)

        dreamData.dreams.add(dream)
        dreamData.updated = Instant.now() //Manually because dreams are only referencing the dream data

        dreamDataRepository.save(dreamData)

        return dream
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun deleteDream(
        @Argument id: Long,
        @AuthenticationPrincipal user: User
    ): DreamEntity {
        val dream = dreamRepository.findById(id).orElseThrow { AboutMeException.NotFoundException(id) }
        val dreamData = dream.dreamData

        if(dreamData.dayData.user.id != user.id) throw AboutMeException.NotFoundException(id)

        dreamData.dreams.removeIf { it.id == id }
        dreamData.updated = Instant.now()

        dreamRepository.delete(dream)
        dreamDataRepository.save(dreamData)

        return dream
    }

}