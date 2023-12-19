package com.snow.aboutme.data.model.day;

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/**
 * Represents one day of mood data for one user
 */
@Entity
class MoodData(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

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

)