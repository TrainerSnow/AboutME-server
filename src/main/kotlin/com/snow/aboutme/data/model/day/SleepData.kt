package com.snow.aboutme.data.model.day;

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/**
 * Represents one day of sleeping data for one user
 */
@Entity
class SleepData (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val hoursSlept: Int = 0,

    @Column(nullable = true)
    val hoursAim: Int? = null

)