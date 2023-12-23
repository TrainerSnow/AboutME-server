package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.DreamEntity
import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany

@Entity
class DreamDataEntity(

    @OneToMany(mappedBy = "dreamData")
    var dreams: MutableSet<DreamEntity> = mutableSetOf()

) : AbstractEntity()

fun DreamDataEntity?.createOrUpdate(
    id: Long? = this?.id,
    dreams: MutableSet<DreamEntity> = this?.dreams ?: mutableSetOf()
) = this?.apply {
    this.id = id
    this.dreams = dreams
} ?: DreamDataEntity(dreams).apply { this.id = id }