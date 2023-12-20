package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import com.snow.aboutme.data.model.day.DiaryData
import com.snow.aboutme.data.model.day.DreamData
import com.snow.aboutme.data.model.day.MoodData
import com.snow.aboutme.data.model.day.SleepData
import jakarta.persistence.*

@Entity
class DayData(

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = true)
    @JoinColumn(name = "diary_data_id", nullable = true)
    val diaryData: DiaryData? = null,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = true)
    @JoinColumn(name = "dream_data_id", nullable = true)
    val dreamData: DreamData? = null,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = true)
    @JoinColumn(name = "mood_data_id", nullable = true)
    val moodData: MoodData? = null,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = true)
    @JoinColumn(name = "sleep_data_id", nullable = true)
    val sleepData: SleepData? = null,


    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User? = null

): AbstractEntity()