package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import com.snow.aboutme.data.model.day.DiaryDataEntity
import com.snow.aboutme.data.model.day.DreamDataEntity
import com.snow.aboutme.data.model.day.MoodDataEntity
import com.snow.aboutme.data.model.day.SleepDataEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class DayDataEntity : AbstractEntity() {

    @Column(nullable = false)
    var date: LocalDate = LocalDate.now()

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = true)
    @JoinColumn(name = "diary_data_id", nullable = true)
    var diaryData: DiaryDataEntity? = null

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = true)
    @JoinColumn(name = "dream_data_id", nullable = true)
    var dreamData: DreamDataEntity? = null

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = true)
    @JoinColumn(name = "mood_data_id", nullable = true)
    var moodData: MoodDataEntity? = null

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = true)
    @JoinColumn(name = "sleep_data_id", nullable = true)
    var sleepData: SleepDataEntity? = null


    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

}