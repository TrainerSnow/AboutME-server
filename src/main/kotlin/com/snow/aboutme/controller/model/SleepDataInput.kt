package com.snow.aboutme.controller.model

import java.time.Instant
import java.time.LocalDate

data class SleepDataInput(

    val hoursSlept: Int,

    val hoursAim: Int?,

    val date: LocalDate,

    val updated: Instant,
    val created: Instant

)
