package com.snow.aboutme.controller;

import com.snow.aboutme.annotation.GraphQLAuthenticated
import com.snow.aboutme.controller.model.CreateDreamInput
import com.snow.aboutme.controller.model.DreamDataInput
import com.snow.aboutme.controller.model.UpdateDreamInput
import com.snow.aboutme.data.model.DreamEntity
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.day.DreamDataEntity
import com.snow.aboutme.data.repository.DreamDataRepository
import com.snow.aboutme.data.repository.DreamRepository
import com.snow.aboutme.exception.AboutMeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import java.time.LocalDate

@Controller
class DreamController {

    @Autowired
    lateinit var dreamRepository: DreamRepository

    @Autowired
    lateinit var dreamDataRepository: DreamDataRepository

    @GraphQLAuthenticated
    @MutationMapping
    fun addDream(
        @AuthenticationPrincipal user: User,
        @Argument dreamInput: CreateDreamInput
    ): DreamEntity {
        val dataForDate = dreamDataRepository.findByDateAndUser(dreamInput.date, user)

        val new = DreamEntity(
            dreamInput.description,
            dreamInput.annotation,
            dreamInput.clearness,
            dreamInput.mood,
            mutableSetOf(),
            dreamInput.date,
            user = user,
            dreamData = dataForDate
        )
        new.updated = dreamInput.updated
        new.created = dreamInput.created

        return dreamRepository.save(new)
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun updateDream(
        @AuthenticationPrincipal user: User,
        @Argument updateDreamInput: UpdateDreamInput,
        @Argument id: Long
    ): DreamEntity {
        val existing = dreamRepository.findByIdAndUser(id, user) ?: throw AboutMeException.NotFoundException(id)
        val dataForDate = dreamDataRepository.findByDateAndUser(updateDreamInput.date, user)

        existing.description = updateDreamInput.description
        existing.annotation = updateDreamInput.annotation
        existing.clearness = updateDreamInput.clearness
        existing.mood = updateDreamInput.mood
        existing.date = updateDreamInput.date
        existing.updated = updateDreamInput.updated
        existing.dreamData = dataForDate

        return dreamRepository.save(existing)
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun deleteDream(
        @AuthenticationPrincipal user: User,
        @Argument id: Long
    ): DreamEntity {
        val entity = dreamRepository.findByIdAndUser(id, user) ?: throw AboutMeException.NotFoundException(id)

        dreamRepository.delete(entity)
        return entity
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun addDreamData(
        @AuthenticationPrincipal user: User,
        @Argument dreamDataInput: DreamDataInput
    ): DreamDataEntity {
        val existing = dreamDataRepository.findByDateAndUser(dreamDataInput.date, user)
        val dreamsForDate = dreamRepository.findAllByDateAndUser(dreamDataInput.date, user)

        val toSave = if (existing == null) {
            val new = DreamDataEntity(
                dreams = dreamsForDate.toMutableSet(),
                user = user,
                date = dreamDataInput.date
            )
            new.updated = dreamDataInput.updated
            new.created = dreamDataInput.created

            dreamDataRepository.save(new)
        } else {
            existing.dreams = dreamsForDate.toMutableSet()
            existing.date = dreamDataInput.date
            existing.updated = dreamDataInput.updated

            dreamDataRepository.save(existing)
        }

        dreamsForDate.forEach { dream ->
            dream.dreamData = toSave
            dreamRepository.save(dream)
        }

        dreamDataRepository.save(toSave)
        return toSave
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun deleteDreamData(
        @AuthenticationPrincipal user: User,
        @Argument date: LocalDate
    ): DreamDataEntity {
        val entity = dreamDataRepository.findByDateAndUser(date, user) ?: throw AboutMeException.NotFoundException(date)

        val dreamsForDate = dreamRepository.findAllByDateAndUser(date, user)
        dreamsForDate.forEach { dream ->
            dream.dreamData = null
            dreamRepository.save(dream)
        }

        dreamDataRepository.delete(entity)
        return entity
    }


    @GraphQLAuthenticated
    @QueryMapping
    fun getDream(
        @AuthenticationPrincipal user: User,
        @Argument id: Long
    ): DreamEntity {
        val entity = dreamRepository.findByIdAndUser(id, user) ?: throw AboutMeException.NotFoundException(id)

        return entity
    }

    @GraphQLAuthenticated
    @QueryMapping
    fun getAllDreams(
        @AuthenticationPrincipal user: User
    ): List<DreamEntity> {
        val all = dreamRepository.findAllByUser(user)

        return all
    }

    @GraphQLAuthenticated
    @QueryMapping
    fun getDreamData(
        @AuthenticationPrincipal user: User,
        @Argument date: LocalDate
    ): DreamDataEntity {
        val entity = dreamDataRepository.findByDateAndUser(date, user) ?: throw AboutMeException.NotFoundException(date)

        return entity
    }


    @GraphQLAuthenticated
    @QueryMapping
    fun getAllDreamDatas(
        @AuthenticationPrincipal user: User
    ): List<DreamDataEntity> {
        val all = dreamDataRepository.findAllByUser(user)

        return all
    }

}