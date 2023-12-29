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

fun NameInfo.update(
    id: Long? = this.id,
    firstName: String = this.firstName,
    middleName: String? = this.middleName,
    lastName: String? = this.middleName,
    title: String? = this.title
) = apply {
    this.id = id
    this.firstName = firstName
    this.middleName = middleName
    this.lastName = lastName
    this.title = title
}