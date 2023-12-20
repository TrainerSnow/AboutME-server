package com.snow.aboutme.data.model.day;

import jakarta.persistence.*

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