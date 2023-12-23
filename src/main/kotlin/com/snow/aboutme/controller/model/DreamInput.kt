package com.snow.aboutme.controller.model;

data class DreamInput (

    val description: String,

    val annotation: String?,

    val clearness: Float?,

    val mood: Float?,

    val persons: List<Long>?

)