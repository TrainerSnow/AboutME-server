package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

/**
 * Represents one day of sleeping data for one user
 */
@Entity
class SleepDataEntity(

    @Column(nullable = false)
    var hoursSlept: Int = 0,

    @Column(nullable = true)
    var hoursAim: Int? = null

) : AbstractEntity()