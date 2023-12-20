package com.snow.aboutme.data.model;

import jakarta.persistence.*

@Entity
class Person(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,


    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = false)
    @JoinColumn(name = "name_info_id", nullable = false)
    val nameInfo: NameInfo? = null,


    @ManyToOne(optional = false)
    @JoinColumn(name = "person_relation_id", nullable = false)
    val relation: PersonRelation? = null,

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    val user: User? = null,


    @ManyToMany(mappedBy = "persons")
    val dreams: MutableSet<Dream> = mutableSetOf()

)