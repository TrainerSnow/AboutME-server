package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import com.snow.aboutme.data.model.day.DreamData
import jakarta.persistence.*

@Entity
class Dream(

    @Column(nullable = false)
    val description: String = "",

    @Column(nullable = true)
    val annotation: String? = null,

    @Column(nullable = true)
    val clearness: Float? = null,

    @Column(nullable = true)
    val mood: Float? = null,


    @ManyToOne(optional = false)
    @JoinColumn(name = "dream_data_id", nullable = false)
    val dreamData: DreamData? = null,


    @ManyToMany
    @JoinTable(
        name = "dream_person_cross",
        joinColumns = [JoinColumn(name = "dream_id")],
        inverseJoinColumns = [JoinColumn(name = "person_id")]
    )
    val persons: MutableSet<Person> = mutableSetOf()

): AbstractEntity()