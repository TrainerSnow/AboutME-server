package com.snow.aboutme.controller.model;

import java.time.Instant
import java.time.LocalDate

data class MoodDataInput (

    val mood: Float?,

    val moodMorning: Float?,
    val moodNoon: Float?,
    val moodEvening: Float?,

    val date: LocalDate,

    val created: Instant,
    val updated: Instant

)