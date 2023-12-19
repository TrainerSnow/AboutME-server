package com.snow.aboutme.data.model.day;

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class DiaryData(

    @Id
    @GeneratedValue
    val id: Long? = null,

    @Column(nullable = false)
    val diaryContent: String = ""

)