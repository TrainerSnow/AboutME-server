package com.snow.aboutme.controller

import com.snow.aboutme.annotation.GraphQLAuthenticated
import com.snow.aboutme.controller.model.SleepDataInput
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.day.SleepDataEntity
import com.snow.aboutme.data.repository.SleepDataRepository
import com.snow.aboutme.exception.AboutMeException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.graphql.data.method.annotation.Argument
import org.springframework.graphql.data.method.annotation.MutationMapping
import org.springframework.graphql.data.method.annotation.QueryMapping
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.stereotype.Controller
import java.time.LocalDate

@Controller
class SleepController {

    @Autowired
    private lateinit var sleepDataRepository: SleepDataRepository

    @GraphQLAuthenticated
    @MutationMapping
    fun addSleepData(
        @AuthenticationPrincipal user: User,
        @Argument sleepDataInput: SleepDataInput,
    ): SleepDataEntity {
        val existing = sleepDataRepository.findByDateAndUser(sleepDataInput.date, user)

        return if (existing == null) {
            val new = SleepDataEntity(sleepDataInput.hoursSlept, sleepDataInput.hoursAim, sleepDataInput.date, user)
            new.created = sleepDataInput.created
            new.updated = sleepDataInput.updated

            sleepDataRepository.save(new)
        } else {
            existing.updated = sleepDataInput.updated
            existing.hoursAim = sleepDataInput.hoursAim
            existing.hoursSlept = sleepDataInput.hoursSlept
            existing.date = sleepDataInput.date

            sleepDataRepository.save(existing)
        }
    }

    @GraphQLAuthenticated
    @MutationMapping
    fun deleteSleepData(
        @AuthenticationPrincipal user: User,
        @Argument date: LocalDate
    ): SleepDataEntity {
        val data = sleepDataRepository.findByDateAndUser(date, user) ?: throw AboutMeException.NotFoundException(date)

        sleepDataRepository.delete(data)
        return data
    }

    @GraphQLAuthenticated
    @QueryMapping
    fun getSleepDataByDate(
        @AuthenticationPrincipal user: User,
        @Argument date: LocalDate
    ): SleepDataEntity {
        val data = sleepDataRepository.findByDateAndUser(date, user) ?: throw AboutMeException.NotFoundException(date)

        return data
    }

    @GraphQLAuthenticated
    @QueryMapping
    fun getAllSleepDatas(
        @AuthenticationPrincipal user: User
    ): List<SleepDataEntity> {
        return sleepDataRepository.findAllByUser(user)
    }

}