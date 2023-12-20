package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.*
import java.time.Instant

@Entity
class RefreshToken (

    @Column(nullable = false)
    val expirationDate: Instant = Instant.now(),


    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = true)
    val user: User? = null

): AbstractEntity()