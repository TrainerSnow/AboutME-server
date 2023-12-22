package com.snow.aboutme.data.graphql;

data class NameInfoInput(

    val firstName: String,

    val middleName: String?,

    val lastName: String?,

    val title: String?

)