package com.snow.aboutme.data.model

import jakarta.persistence.*

/**
 * Represents a user in the database
 */
@Entity
@Table(name = "users")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "name_info_id")
    val nameInfo: NameInfo = NameInfo(),

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    val persons: List<Person> = emptyList(),

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    val personRelations: List<PersonRelation> = emptyList(),

    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_id")
    val dayData: List<DayData> = emptyList(),

    @Column(nullable = false, unique = true)
    val email: String = "",

    @Column(nullable = false)
    val passwordHash: String = ""

)
