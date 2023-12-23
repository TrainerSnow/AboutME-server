package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import com.snow.aboutme.data.model.day.DiaryDataEntity
import com.snow.aboutme.data.model.day.DreamDataEntity
import com.snow.aboutme.data.model.day.MoodDataEntity
import com.snow.aboutme.data.model.day.SleepDataEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction
import java.time.LocalDate

@Entity
class DayDataEntity(

    @Column(nullable = false)
    var date: LocalDate = LocalDate.now(),

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = true)
    @JoinColumn(name = "diary_data_id", nullable = true)
    var diaryData: DiaryDataEntity? = null,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = true)
    @JoinColumn(name = "dream_data_id", nullable = true)
    var dreamData: DreamDataEntity? = null,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = true)
    @JoinColumn(name = "mood_data_id", nullable = true)
    var moodData: MoodDataEntity? = null,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = true)
    @JoinColumn(name = "sleep_data_id", nullable = true)
    var sleepData: SleepDataEntity? = null

) : AbstractEntity() {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    constructor(
        date: LocalDate,
        diaryData: DiaryDataEntity? = null,
        dreamData: DreamDataEntity? = null,
        moodData: MoodDataEntity? = null,
        sleepData: SleepDataEntity? = null,
        user: User
    ) : this(date, diaryData, dreamData, moodData, sleepData) {
        this.user = user
    }

}