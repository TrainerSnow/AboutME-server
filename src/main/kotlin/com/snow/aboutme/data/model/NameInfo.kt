package com.snow.aboutme.data.model;

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id

/**
 * Information about a nameable record in the database
 */
@Entity
class NameInfo(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val firstName: String = "",

    @Column(nullable = true)
    val middleName: String? = null,

    @Column(nullable = true)
    val lastName: String? = null,

    @Column(nullable = true)
    val title: String? = null

)