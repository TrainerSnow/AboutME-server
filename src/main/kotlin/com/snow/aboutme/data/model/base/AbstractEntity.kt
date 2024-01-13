package com.snow.aboutme.data.model.base;

import jakarta.persistence.*
import java.time.Instant

@MappedSuperclass
abstract class AbstractEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var created: Instant = Instant.now(),

    @Column(nullable = true)
    var updated: Instant = Instant.now()

)