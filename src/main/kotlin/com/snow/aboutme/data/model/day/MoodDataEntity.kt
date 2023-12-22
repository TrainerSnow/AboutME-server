package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

/**
 * Represents one day of mood data for one user
 */
@Entity
class MoodDataEntity(

    @Column(nullable = false)
    var constant: Boolean = true,

    @Column(nullable = true)
    var moodLevel: Float? = null,

    @Column(nullable = true)
    var moodMorning: Float? = null,

    @Column(nullable = true)
    var moodNoon: Float? = null,

    @Column(nullable = true)
    var moodEvening: Float? = null

) : AbstractEntity()