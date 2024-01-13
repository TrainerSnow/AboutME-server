package com.snow.aboutme.controller.model

import java.time.Instant

data class CreatePersonInput(

    val nameInfoInput: NameInfoInput,

    val personRelationId: Long,

    val created: Instant,
    val updated: Instant
)

data class UpdatePersonInput(

    val nameInfoInput: NameInfoInput,

    val personRelationId: Long,

    val updated: Instant
)
