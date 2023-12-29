package com.snow.aboutme.controller;

import com.snow.aboutme.annotation.GraphQLAuthenticated
import com.snow.aboutme.controller.model.DiaryDataInput
import com.snow.aboutme.data.model.DayDataEntity
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.day.DiaryDataEntity
import com.snow.aboutme.data.model.day.createOrUpdate
import com.snow.aboutme.data.repository.DayDataRepository
import com.snow.aboutme.data.repository.DiaryDataRepository
import com.snow.aboutme.exception.AboutMeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import java.time.LocalDate

@Controller
class DiaryController {

    @Autowired
    private lateinit var dayDataRepository: DayDataRepository

    @Autowired
    private lateinit var diaryDataRepository: DiaryDataRepository

    @GraphQLAuthenticated
    @MutationMapping
    fun addDiaryData(
        @Argument diaryDataInput: DiaryDataInput,
        @Argument date: LocalDate,
        @AuthenticationPrincipal user: User
    ): DiaryDataEntity {
        val day =
            dayDataRepository.findByUserAndDate(user, date).orElseThrow { AboutMeException.NotFoundException(date) }

        val diaryData = day.diaryData.createOrUpdate(
            diaryContent = diaryDataInput.diaryContent,
            date = date
        )
        day.diaryData = diaryData

        diaryDataRepository.save(diaryData)
        dayDataRepository.save(day)

        return diaryData
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun deleteDiaryData(
        @Argument date: LocalDate,
        @AuthenticationPrincipal user: User
    ): DiaryDataEntity {
        val day: DayDataEntity? = dayDataRepository.findByUserAndDate(user, date).orElse(null)
        val diaryData = day?.diaryData ?: throw AboutMeException.NotFoundException(date)

        day.diaryData = null
        dayDataRepository.save(day)
        return diaryData
    }

}