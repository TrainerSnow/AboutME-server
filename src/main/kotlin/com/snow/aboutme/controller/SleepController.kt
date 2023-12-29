package com.snow.aboutme.controller

import com.snow.aboutme.annotation.GraphQLAuthenticated
import com.snow.aboutme.controller.model.SleepDataInput
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.day.SleepDataEntity
import com.snow.aboutme.data.model.day.createOrUpdate
import com.snow.aboutme.data.repository.DayDataRepository
import com.snow.aboutme.data.repository.SleepDataRepository
import com.snow.aboutme.exception.AboutMeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import java.time.LocalDate

@Controller
class SleepController {

    @Autowired
    lateinit var dayDataRepository: DayDataRepository

    @Autowired
    private lateinit var sleepDataRepository: SleepDataRepository

    @GraphQLAuthenticated
    @MutationMapping
    fun addSleepData(
        @Argument sleepDataInput: SleepDataInput,
        @Argument date: LocalDate,
        @AuthenticationPrincipal user: User
    ): SleepDataEntity {
        val day =
            dayDataRepository.findByUserAndDate(user, date).orElseThrow { AboutMeException.NotFoundException(date) }

        val sleepData = day.sleepData.createOrUpdate(
            hoursSlept = sleepDataInput.hoursSlept,
            hoursAim = sleepDataInput.hoursAim
        ).let(sleepDataRepository::save)

        day.sleepData = sleepData

        dayDataRepository.save(day)

        return sleepData
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun deleteSleepData(
        @Argument date: LocalDate,
        @AuthenticationPrincipal user: User
    ): SleepDataEntity {
        val day = dayDataRepository.findByUserAndDate(user, date).orElseThrow { AboutMeException.NotFoundException(date) }
        val sleepData = day.sleepData ?: throw AboutMeException.NotFoundException(date)

        day.sleepData = null
        dayDataRepository.save(day)
        sleepDataRepository.delete(sleepData)

        return sleepData
    }

}