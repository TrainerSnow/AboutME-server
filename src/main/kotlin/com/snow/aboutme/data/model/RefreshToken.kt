package com.snow.aboutme.data.model;

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.time.Instant

@Entity
class RefreshToken (

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: String? = null,

    @Column(nullable = false)
    val expirationDate: Instant = Instant.now(),

    @OneToOne
    @JoinColumn(name = "user_id")
    val user: User = User()

)