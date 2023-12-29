package com.snow.aboutme.controller;

import com.snow.aboutme.annotation.GraphQLAuthenticated
import com.snow.aboutme.data.model.DayDataEntity
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.repository.DayDataRepository
import com.snow.aboutme.data.repository.UserRepository
import com.snow.aboutme.exception.AboutMeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import java.time.LocalDate

@Controller
class DayController {

    @Autowired
    private lateinit var dayDataRepository: DayDataRepository

    @Autowired
    private lateinit var userRepository: UserRepository

    @GraphQLAuthenticated
    @MutationMapping
    fun createDayData(
        @Argument date: LocalDate,
        @AuthenticationPrincipal user: User
    ): DayDataEntity {
        if (dayDataRepository.findByUserAndDate(user, date).isPresent) throw AboutMeException.Conflict(date)

        return DayDataEntity(
            date = date,
            user = user
        ).let(dayDataRepository::save)
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun deleteDayData(
        @Argument date: LocalDate,
        @AuthenticationPrincipal user: User
    ): DayDataEntity {
        val day =
            dayDataRepository.findByUserAndDate(user, date).orElseThrow { AboutMeException.NotFoundException(date) }

        user.dayData.removeIf { it.date == date }
        userRepository.save(user)
        dayDataRepository.delete(day)
        return day
    }

    @GraphQLAuthenticated
    @QueryMapping
    fun dayDatas(
        @AuthenticationPrincipal user: User
    ): Set<DayDataEntity> = user.dayData

    @GraphQLAuthenticated
    @QueryMapping
    fun dayData(
        @Argument date: LocalDate,
        @AuthenticationPrincipal user: User
    ): DayDataEntity {
        return dayDataRepository.findByUserAndDate(user, date).orElseThrow { AboutMeException.NotFoundException(date) }
    }

}