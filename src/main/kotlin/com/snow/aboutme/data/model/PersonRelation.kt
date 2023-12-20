package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.*

/**
 * Represents a relation that a user may have to a person
 */
@Entity
class PersonRelation(

    @Column(nullable = false)
    val name: String = "",

    @Column(nullable = true)
    val color: String? = null,


    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User? = null,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "relation")
    val persons: Set<Person> = emptySet()

): AbstractEntity()