package com.snow.aboutme.controller;

import com.snow.aboutme.annotation.GraphQLAuthenticated
import com.snow.aboutme.controller.model.MoodDataInput
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.day.MoodDataEntity
import com.snow.aboutme.data.repository.MoodDataRepository
import com.snow.aboutme.exception.AboutMeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import java.time.LocalDate

@Controller
class MoodController {

    @Autowired
    private lateinit var moodDataRepository: MoodDataRepository

    @GraphQLAuthenticated
    @MutationMapping
    fun addMoodData(
        @AuthenticationPrincipal user: User,
        @Argument moodDataInput: MoodDataInput,
    ): MoodDataEntity {
        val existing = moodDataRepository.findByDateAndUser(moodDataInput.date, user)

        return if (existing == null) {
            val new = MoodDataEntity(
                moodDataInput.mood != null,
                moodDataInput.mood,
                moodDataInput.moodMorning,
                moodDataInput.moodNoon,
                moodDataInput.moodEvening,
                moodDataInput.date,
                user
            )
            new.created = moodDataInput.created
            new.updated = moodDataInput.updated

            moodDataRepository.save(new)
        } else {
            existing.updated = moodDataInput.updated
            existing.constant = moodDataInput.mood != null
            existing.mood = moodDataInput.mood
            existing.moodMorning = moodDataInput.moodMorning
            existing.moodNoon = moodDataInput.moodNoon
            existing.moodEvening = moodDataInput.moodEvening
            existing.date = moodDataInput.date
            existing.date = moodDataInput.date

            moodDataRepository.save(existing)
        }
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun deleteMoodData(
        @AuthenticationPrincipal user: User,
        @Argument date: LocalDate
    ): MoodDataEntity {
        val data = moodDataRepository.findByDateAndUser(date, user) ?: throw AboutMeException.NotFoundException(date)

        moodDataRepository.delete(data)
        return data
    }

    @GraphQLAuthenticated
    @QueryMapping
    fun getMoodDataByDate(
        @AuthenticationPrincipal user: User,
        @Argument date: LocalDate
    ): MoodDataEntity {
        val data = moodDataRepository.findByDateAndUser(date, user) ?: throw AboutMeException.NotFoundException(date)

        return data
    }

    @GraphQLAuthenticated
    @QueryMapping
    fun getAllMoodDatas(
        @AuthenticationPrincipal user: User
    ): List<MoodDataEntity> {
        return moodDataRepository.findAllByUser(user)
    }

}