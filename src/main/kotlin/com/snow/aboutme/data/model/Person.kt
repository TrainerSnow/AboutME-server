package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.*

@Entity
class Person(

    @ManyToMany(mappedBy = "persons")
    val dreams: MutableSet<DreamEntity> = mutableSetOf()

) : AbstractEntity() {

    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = false)
    @JoinColumn(name = "name_info_id", nullable = false)
    lateinit var nameInfo: NameInfo


    @ManyToOne(optional = false)
    @JoinColumn(name = "person_relation_id", nullable = false)
    lateinit var relation: PersonRelation

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    constructor(
        dreams: MutableSet<DreamEntity> = mutableSetOf(),
        nameInfo: NameInfo,
        relation: PersonRelation,
        user: User
    ) : this(dreams) {
        this.nameInfo = nameInfo
        this.relation = relation
        this.user = user
    }

}