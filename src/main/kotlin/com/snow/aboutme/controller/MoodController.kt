package com.snow.aboutme.controller;

import com.snow.aboutme.annotation.GraphQLAuthenticated
import com.snow.aboutme.controller.model.MoodInput
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.day.MoodDataEntity
import com.snow.aboutme.data.model.day.createOrUpdate
import com.snow.aboutme.data.repository.DayDataRepository
import com.snow.aboutme.data.repository.MoodDataRepository
import com.snow.aboutme.exception.AboutMeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import java.time.LocalDate

//TODO: Mood Data handling: We pass all four mood values around as nullable. Is bad
@Controller
class MoodController {

    @Autowired
    private lateinit var dayDataRepository: DayDataRepository

    @Autowired
    private lateinit var moodDataRepository: MoodDataRepository

    @GraphQLAuthenticated
    @MutationMapping
    fun addMoodData(
        @Argument moodDataInput: MoodInput,
        @Argument date: LocalDate,
        @AuthenticationPrincipal user: User
    ): MoodDataEntity {
        val day =
            dayDataRepository.findByUserAndDate(user, date).orElseThrow { AboutMeException.NotFoundException(date) }

        val moodData = if (moodDataInput.mood != null) {
            day.moodData.createOrUpdate(
                constant = true,
                mood = moodDataInput.mood,
                moodMorning = null,
                moodNoon = null,
                moodEvening = null
            ).let(moodDataRepository::save)
        } else if (moodDataInput.moodMorning != null && moodDataInput.moodNoon != null && moodDataInput.moodEvening != null) {
            day.moodData.createOrUpdate(
                constant = false,
                mood = null,
                moodMorning = moodDataInput.moodMorning,
                moodNoon = moodDataInput.moodNoon,
                moodEvening = moodDataInput.moodEvening
            ).let(moodDataRepository::save)
        } else throw AboutMeException.InvalidData("MoodData needs to either be fully constant or fully dynamic!")

        day.moodData = moodData
        dayDataRepository.save(day)

        return moodData
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun deleteMoodData(
        @Argument date: LocalDate,
        @AuthenticationPrincipal user: User
    ): MoodDataEntity {
        val day = dayDataRepository.findByUserAndDate(user, date).orElse(null)
        val moodData = day?.moodData ?: throw AboutMeException.NotFoundException(date)

        day.moodData = null
        dayDataRepository.save(day)
        moodDataRepository.delete(moodData)

        return moodData
    }

}