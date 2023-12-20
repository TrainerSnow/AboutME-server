package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.*

/**
 * Represents one day of mood data for one user
 */
@Entity
class MoodData(

    @Column(nullable = false)
    val constant: Boolean = true,

    @Column(nullable = true)
    val moodLevel: Float? = null,

    @Column(nullable = true)
    val moodMorning: Float? = null,

    @Column(nullable = true)
    val moodNoon: Float? = null,

    @Column(nullable = true)
    val moodEvening: Float? = null,

): AbstractEntity()