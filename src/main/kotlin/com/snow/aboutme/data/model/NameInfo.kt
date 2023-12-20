package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.*

/**
 * Information about a nameable record in the database
 */
@Entity
class NameInfo(

    @Column(nullable = false)
    val firstName: String = "",

    @Column(nullable = true)
    val middleName: String? = null,

    @Column(nullable = true)
    val lastName: String? = null,

    @Column(nullable = true)
    val title: String? = null

): AbstractEntity()