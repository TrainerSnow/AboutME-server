package com.snow.aboutme.controller.model

import java.time.Instant
import java.time.LocalDate

data class DiaryDataInput(

    val diaryContent: String,

    val date: LocalDate,

    val created: Instant,
    val updated: Instant

)
