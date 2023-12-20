package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.*

@Entity
class Person : AbstractEntity() {

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = false)
    @JoinColumn(name = "name_info_id", nullable = false)
    lateinit var nameInfo: NameInfo


    @ManyToOne(optional = false)
    @JoinColumn(name = "person_relation_id", nullable = false)
    lateinit var relation: PersonRelation

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User


    @ManyToMany(mappedBy = "persons")
    val dreams: MutableSet<DreamEntity> = mutableSetOf()

}