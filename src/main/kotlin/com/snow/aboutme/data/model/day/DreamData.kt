package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.Dream
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany

@Entity
class DreamData(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @OneToMany
    @JoinColumn(name = "dream_data_id")
    val dreams: List<Dream> = emptyList()

)