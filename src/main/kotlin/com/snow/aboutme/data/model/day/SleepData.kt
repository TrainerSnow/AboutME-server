package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.*

/**
 * Represents one day of sleeping data for one user
 */
@Entity
class SleepData (

    @Column(nullable = false)
    val hoursSlept: Int = 0,

    @Column(nullable = true)
    val hoursAim: Int? = null

): AbstractEntity()