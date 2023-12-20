package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.Dream
import jakarta.persistence.*

@Entity
class DreamData(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToMany(mappedBy = "dreamData")
    val dreams: Set<Dream> = emptySet()

)