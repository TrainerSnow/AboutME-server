package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.*

/**
 * Represents a relation that a user may have to a person
 */
@Entity
class PersonRelation(

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = true)
    var color: String? = null,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "relation")
    var persons: MutableSet<Person> = mutableSetOf()

) : AbstractEntity() {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    constructor(
        name: String,
        color: String?,
        persons: MutableSet<Person>,
        user: User
    ) : this(name, color, persons) {
        this.user = user
    }

}