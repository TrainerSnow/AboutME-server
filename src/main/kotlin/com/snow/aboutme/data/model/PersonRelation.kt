package com.snow.aboutme.data.model;

import jakarta.persistence.*

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
    val user: User = User(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "person_relation_id")
    val persons: Set<Person> = emptySet()

)