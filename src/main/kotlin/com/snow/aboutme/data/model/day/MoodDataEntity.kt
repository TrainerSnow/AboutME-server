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
    var mood: Float? = null,

    @Column(nullable = true)
    var moodMorning: Float? = null,

    @Column(nullable = true)
    var moodNoon: Float? = null,

    @Column(nullable = true)
    var moodEvening: Float? = null

) : AbstractEntity()

fun MoodDataEntity?.createOrUpdate(
    id: Long? = this?.id,
    constant: Boolean = true,
    mood: Float? = this?.mood,
    moodMorning: Float? = this?.moodMorning,
    moodNoon: Float? = this?.moodNoon,
    moodEvening: Float? = this?.moodEvening
) = this?.apply {
    this.id = id
    this.constant = constant
    this.mood = mood
    this.moodMorning = moodMorning
    this.moodNoon = moodNoon
    this.moodEvening = moodEvening
} ?: MoodDataEntity(constant, mood, moodMorning, moodNoon, moodEvening).apply { this.id = id }