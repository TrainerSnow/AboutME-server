package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id

@Entity
class DiaryData(

    @Column(nullable = false)
    val diaryContent: String = ""

): AbstractEntity()