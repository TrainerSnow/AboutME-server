package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import com.snow.aboutme.data.model.day.DreamDataEntity
import jakarta.persistence.*

@Entity
class DreamEntity : AbstractEntity() {

    @Column(nullable = false)
    var description: String = ""

    @Column(nullable = true)
    var annotation: String? = null

    @Column(nullable = true)
    var clearness: Float? = null

    @Column(nullable = true)
    var mood: Float? = null


    @ManyToOne(optional = false)
    @JoinColumn(name = "dream_data_id", nullable = false)
    lateinit var dreamData: DreamDataEntity


    @ManyToMany
    @JoinTable(
        name = "dream_person_cross",
        joinColumns = [JoinColumn(name = "dream_id")],
        inverseJoinColumns = [JoinColumn(name = "person_id")]
    )
    var persons: MutableSet<Person> = mutableSetOf()

}