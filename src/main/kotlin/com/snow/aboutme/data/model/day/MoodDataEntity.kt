package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate

/**
 * Represents one day of mood data for one user
 */
@Entity
class MoodDataEntity(

    @Column(nullable = false)
    var constant: Boolean = true,

    @Column(nullable = true)
    var mood: Float? = null,

    @Column(nullable = true)
    var moodMorning: Float? = null,

    @Column(nullable = true)
    var moodNoon: Float? = null,

    @Column(nullable = true)
    var moodEvening: Float? = null,

    @Column(nullable = false)
    var date: LocalDate = LocalDate.now()

) : AbstractEntity() {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    constructor(
        constant: Boolean,
        mood: Float?,
        moodMorning: Float?,
        moodNoon: Float?,
        moodEvening: Float?,
        date: LocalDate,
        user: User
    ) : this(constant, mood, moodMorning, moodNoon, moodEvening, date) {
        this.user = user
    }

}

