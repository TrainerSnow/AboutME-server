package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.DreamEntity
import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class DreamDataEntity(

    @OneToMany(mappedBy = "dreamData")
    var dreams: Set<DreamEntity> = emptySet()

): AbstractEntity()