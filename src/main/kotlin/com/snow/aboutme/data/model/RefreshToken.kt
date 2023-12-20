package com.snow.aboutme.data.model;

import jakarta.persistence.*
import java.time.Instant

@Entity
class RefreshToken (

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    @Column(nullable = false)
    val expirationDate: Instant = Instant.now(),


    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = true)
    val user: User? = null

)