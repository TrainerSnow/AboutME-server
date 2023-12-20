package com.snow.aboutme.data.model;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToOne
import java.time.Instant

@Entity
class RefreshToken : AbstractEntity() {

    @Column(nullable = false)
    val expirationDate: Instant = Instant.now()


    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

}