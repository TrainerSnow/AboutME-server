package com.snow.aboutme.controller.model

import java.time.Instant

data class CreatePersonRelationInput(

    val name: String,

    val color: String?,

    val created: Instant,
    val updated: Instant
)

data class UpdatePersonRelationInput(

    val name: String,

    val color: String?,

    val updated: Instant
)
