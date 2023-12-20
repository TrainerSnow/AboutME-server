package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

@Entity
class DiaryDataEntity(

    @Column(nullable = false)
    var diaryContent: String = ""

) : AbstractEntity()