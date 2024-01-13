package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import java.time.LocalDate

/**
 * Represents one day of sleeping data for one user
 */
@Entity
class SleepDataEntity(

    @Column(nullable = false)
    var hoursSlept: Int = 0,

    @Column(nullable = true)
    var hoursAim: Int? = null,

    @Column(nullable = false)
    var date: LocalDate = LocalDate.now()

) : AbstractEntity() {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    constructor(
        hoursSlept: Int,
        hoursAim: Int?,
        date: LocalDate,
        user: User
    ) : this(hoursSlept, hoursAim, date) {
        this.user = user
    }

}