package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.day.DiaryData
import com.snow.aboutme.data.model.day.DreamData
import com.snow.aboutme.data.model.day.MoodData
import com.snow.aboutme.data.model.day.SleepData
import jakarta.persistence.*

@Entity
class DayData(

    @Id
    @GeneratedValue
    val id: Long? = null,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "diary_data_id")
    val diaryData: DiaryData = DiaryData(),

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "dream_data_id")
    val dreamData: DreamData = DreamData(),

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "mood_data_id")
    val moodData: MoodData = MoodData(),

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "sleep_data_id")
    val sleepData: SleepData = SleepData(),

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User = User()

)