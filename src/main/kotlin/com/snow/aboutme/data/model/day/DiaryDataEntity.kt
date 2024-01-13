package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate

@Entity
class DiaryDataEntity(

    @Column(nullable = false)
    var diaryContent: String = "",

    @Column(nullable = false)
    var date: LocalDate = LocalDate.now()

) : AbstractEntity(){

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    constructor(
        diaryContent: String,
        date: LocalDate,
        user: User
    ) : this(diaryContent, date) {
        this.user = user
    }

}

