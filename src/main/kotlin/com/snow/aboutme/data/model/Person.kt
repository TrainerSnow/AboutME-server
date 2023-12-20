package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.*

@Entity
class Person(

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

): AbstractEntity()