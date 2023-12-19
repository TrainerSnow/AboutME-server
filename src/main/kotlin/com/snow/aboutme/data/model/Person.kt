package com.snow.aboutme.data.model;

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne
import jakarta.persistence.OneToOne

@Entity
class Person(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true)
    @JoinColumn(name = "name_info_id")
    val nameInfo: NameInfo = NameInfo(),

    @ManyToOne
    @JoinColumn(name = "person_relation_id")
    val relation: PersonRelation = PersonRelation(),

    @ManyToOne
    @JoinColumn(name = "user_id")
    val user: User = User(),

    @ManyToMany(mappedBy = "persons")
    val dreams: Set<Dream> = emptySet()

)