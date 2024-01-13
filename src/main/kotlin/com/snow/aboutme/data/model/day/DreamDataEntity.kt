package com.snow.aboutme.data.model.day;

import com.snow.aboutme.data.model.DreamEntity
import com.snow.aboutme.data.model.User
import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.*
import java.time.LocalDate

@Entity
class DreamDataEntity(

    @OneToMany(mappedBy = "dreamData")
    var dreams: MutableSet<DreamEntity> = mutableSetOf(),

    @Column(nullable = false)
    var date: LocalDate = LocalDate.now()

) : AbstractEntity() {

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    lateinit var user: User

    constructor(
        dreams: MutableSet<DreamEntity>,
        user: User,
        date: LocalDate
    ) : this(dreams, date) {
        this.user = user
    }

}

