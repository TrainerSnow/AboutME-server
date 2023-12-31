package com.snow.aboutme.data.model.base;

import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.MappedSuperclass
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.SourceType
import org.hibernate.annotations.UpdateTimestamp
import java.time.Instant

@MappedSuperclass
abstract class AbstractEntity (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @CreationTimestamp(source = SourceType.DB)
    var created: Instant? = null,

    @UpdateTimestamp(source = SourceType.DB)
    var updated: Instant? = null

)