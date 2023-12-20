package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.Dream
import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.*

@Entity
class DreamData(

    @OneToMany(mappedBy = "dreamData")
    val dreams: Set<Dream> = emptySet()

): AbstractEntity()