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

    @Column(nullable = false, unique = true)
    val email: String = "",

    @Column(nullable = false)
    val passwordHash: String = "",


    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = false)
    @JoinColumn(name = "name_info_id", nullable = false)
    val nameInfo: NameInfo? = null,

    @OneToOne(cascade = [CascadeType.ALL], optional = true)
    @JoinColumn(name = "refresh_token_id", nullable = true)
    val refreshToken: RefreshToken? = null,


    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "user")
    val persons: Set<Person> = emptySet(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "user")
    val personRelations: Set<PersonRelation> = emptySet(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "user")
    val dayData: Set<DayData> = emptySet(),

)
