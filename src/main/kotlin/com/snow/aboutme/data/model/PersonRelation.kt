package com.snow.aboutme.data.model;

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne

/**
 * Represents a relation that a user may have to a person
 */
@Entity
class PersonRelation(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = true)
    val color: String? = null,

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User = User()

)