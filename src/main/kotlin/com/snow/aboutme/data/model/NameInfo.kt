package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity

/**
 * Information about a nameable record in the database
 */
@Entity
class NameInfo(
    
    @Column(nullable = false)
    var firstName: String = "",

    @Column(nullable = true)
    var middleName: String? = null,

    @Column(nullable = true)
    var lastName: String? = null,

    @Column(nullable = true)
    var title: String? = null

): AbstractEntity()