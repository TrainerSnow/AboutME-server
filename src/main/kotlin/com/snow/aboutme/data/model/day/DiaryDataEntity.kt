package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import java.time.LocalDate

@Entity
class DiaryDataEntity(

    @Column(nullable = false)
    var diaryContent: String = "",

    @Column(nullable = false)
    var date: LocalDate = LocalDate.now()

) : AbstractEntity()

fun DiaryDataEntity?.createOrUpdate(
    id: Long? = this?.id,
    diaryContent: String = this?.diaryContent ?: "",
    date: LocalDate = this?.date ?: LocalDate.now()
): DiaryDataEntity = this?.apply {
    this.id = id
    this.diaryContent = diaryContent
    this.date = date
}
    ?: DiaryDataEntity(diaryContent, date).apply { this.id = id }