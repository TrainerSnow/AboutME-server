package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.day.DreamData
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.JoinTable
import jakarta.persistence.ManyToMany
import jakarta.persistence.ManyToOne

@Entity
class Dream(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(nullable = false)
    val description: String = "",

    @Column(nullable = true)
    val annotation: String? = null,

    @Column(nullable = true)
    val clearness: Float? = null,

    @Column(nullable = true)
    val mood: Float? = null,

    @ManyToMany
    @JoinTable(
        name = "dream_person_cross",
        joinColumns = [JoinColumn(name = "dream_id")],
        inverseJoinColumns = [JoinColumn(name = "person_id")]
    )
    val persons: Set<Person> = emptySet(),

    @ManyToOne
    @JoinColumn(name = "dream_data_id")
    val dreamData: DreamData

)