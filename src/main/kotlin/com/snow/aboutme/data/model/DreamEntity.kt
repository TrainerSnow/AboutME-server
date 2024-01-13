package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import com.snow.aboutme.data.model.day.DreamDataEntity
import jakarta.persistence.*
import java.time.LocalDate

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
    var persons: MutableSet<PersonEntity> = mutableSetOf(),

    @Column(nullable = false)
    var date: LocalDate

) : AbstractEntity() {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    @ManyToOne(optional = true)
    @JoinColumn(name = "dream_data_id", nullable = true)
    var dreamData: DreamDataEntity? = null

    constructor(
        description: String,
        annotation: String?,
        clearness: Float?,
        mood: Float?,
        persons: MutableSet<PersonEntity>,
        date: LocalDate,
        user: User,
        dreamData: DreamDataEntity?
    ): this(description, annotation, clearness, mood, persons, date) {
        this.user = user
        this.dreamData = dreamData
    }

}