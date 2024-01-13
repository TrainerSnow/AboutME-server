package com.snow.aboutme.controller.model;

import java.time.Instant
import java.time.LocalDate

data class CreateDreamInput (

    val description: String,

    val annotation: String?,

    val clearness: Float?,

    val mood: Float?,

    val date: LocalDate,

    val created: Instant,
    val updated: Instant

)

data class UpdateDreamInput (

    val description: String,

    val annotation: String?,

    val clearness: Float?,

    val mood: Float?,

    val date: LocalDate,

    val updated: Instant
)