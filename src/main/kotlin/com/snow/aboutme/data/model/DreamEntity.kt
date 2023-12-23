package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import com.snow.aboutme.data.model.day.DreamDataEntity
import jakarta.persistence.*
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
class DreamEntity(

    @Column(nullable = false)
    var description: String = "",

    @Column(nullable = true)
    var annotation: String? = null,

    @Column(nullable = true)
    var clearness: Float? = null,

    @Column(nullable = true)
    var mood: Float? = null,

    @ManyToMany
    @JoinTable(
        name = "dream_person_cross",
        joinColumns = [JoinColumn(name = "dream_id")],
        inverseJoinColumns = [JoinColumn(name = "person_id")]
    )
    var persons: MutableSet<Person> = mutableSetOf()

) : AbstractEntity() {


    @ManyToOne(optional = false)
    @JoinColumn(name = "dream_data_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    lateinit var dreamData: DreamDataEntity

    constructor(
        description: String,
        annotation: String?,
        clearness: Float?,
        mood: Float?,
        persons: MutableSet<Person>,
        dreamData: DreamDataEntity
    ) : this(description, annotation, clearness, mood, persons) {
        this.dreamData = dreamData
    }

}