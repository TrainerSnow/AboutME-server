package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.DayDataEntity
import com.snow.aboutme.data.model.DreamEntity
import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.OneToMany
import jakarta.persistence.OneToOne
import org.hibernate.annotations.OnDelete
import org.hibernate.annotations.OnDeleteAction

@Entity
class DreamDataEntity(

    @OneToMany(mappedBy = "dreamData")
    var dreams: MutableSet<DreamEntity> = mutableSetOf()

) : AbstractEntity() {

    @OneToOne(mappedBy = "dreamData", cascade = [CascadeType.ALL])
    lateinit var dayData: DayDataEntity

}

fun DreamDataEntity?.createOrUpdate(
    id: Long? = this?.id,
    dreams: MutableSet<DreamEntity> = this?.dreams ?: mutableSetOf()
) = this?.apply {
    this.id = id
    this.dreams = dreams
} ?: DreamDataEntity(dreams).apply { this.id = id }