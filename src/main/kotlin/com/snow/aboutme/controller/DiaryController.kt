package com.snow.aboutme.controller;

import com.snow.aboutme.annotation.GraphQLAuthenticated
import com.snow.aboutme.controller.model.DiaryDataInput
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.day.DiaryDataEntity
import com.snow.aboutme.data.repository.DiaryDataRepository
import com.snow.aboutme.exception.AboutMeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import java.time.LocalDate

@Controller
class DiaryController {

    @Autowired
    private lateinit var diaryDataRepository: DiaryDataRepository

    @GraphQLAuthenticated
    @MutationMapping
    fun addDiaryData(
        @AuthenticationPrincipal user: User,
        @Argument diaryDataInput: DiaryDataInput,
    ): DiaryDataEntity {
        val existing = diaryDataRepository.findByDateAndUser(diaryDataInput.date, user)

        return if (existing == null) {
            val new = DiaryDataEntity(diaryDataInput.diaryContent, diaryDataInput.date, user)
            new.created = diaryDataInput.created
            new.updated = diaryDataInput.updated

            diaryDataRepository.save(new)
        } else {
            existing.updated = diaryDataInput.updated
            existing.diaryContent = diaryDataInput.diaryContent
            existing.date = diaryDataInput.date

            diaryDataRepository.save(existing)
        }
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun deleteDiaryData(
        @AuthenticationPrincipal user: User,
        @Argument date: LocalDate
    ): DiaryDataEntity {
        val data = diaryDataRepository.findByDateAndUser(date, user) ?: throw AboutMeException.NotFoundException(date)

        diaryDataRepository.delete(data)
        return data
    }

    @GraphQLAuthenticated
    @QueryMapping
    fun getDiaryDataByDate(
        @AuthenticationPrincipal user: User,
        @Argument date: LocalDate
    ): DiaryDataEntity {
        val data = diaryDataRepository.findByDateAndUser(date, user) ?: throw AboutMeException.NotFoundException(date)

        return data
    }

    @GraphQLAuthenticated
    @QueryMapping
    fun getAllDiaryDatas(
        @AuthenticationPrincipal user: User
    ): List<DiaryDataEntity> {
        return diaryDataRepository.findAllByUser(user)
    }

}