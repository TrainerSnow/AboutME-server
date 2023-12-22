package com.snow.aboutme.data.model;

import jakarta.persistence.*
import java.time.Instant
import java.util.*

@Entity
class RefreshToken(

    @Column(nullable = false)
    val expirationDate: Instant = Instant.now(),

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID = UUID.randomUUID()

) {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    override fun toString(): String {
        return "RefreshToken(expirationDate=$expirationDate, id=$id)"
    }

    constructor(expirationDate: Instant, user: User): this(expirationDate) {
        this.user = user
    }

}