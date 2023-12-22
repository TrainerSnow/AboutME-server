package com.snow.aboutme.data.model

import com.snow.aboutme.data.model.base.AbstractEntity
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

/**
 * Represents a user in the database
 */
@Entity
@Table(name = "users")
class User(

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var passwordHash: String = "",

    @OneToOne(cascade = [CascadeType.ALL], optional = true)
    @JoinColumn(name = "refresh_token_id", nullable = true)
    var refreshToken: RefreshToken? = null,


    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "user", fetch = FetchType.EAGER)
    var persons: Set<Person> = emptySet(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "user", fetch = FetchType.EAGER)
    var personRelations: Set<PersonRelation> = emptySet(),

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, mappedBy = "user", fetch = FetchType.EAGER)
    var dayData: Set<DayDataEntity> = emptySet()

) : AbstractEntity(), UserDetails {


    @OneToOne(cascade = [CascadeType.ALL], orphanRemoval = true, optional = false)
    @JoinColumn(name = "name_info_id", nullable = false)
    lateinit var nameInfo: NameInfo

    constructor(
        email: String,
        passwordHash: String,
        refreshToken: RefreshToken?,
        persons: Set<Person>,
        personRelations: Set<PersonRelation>,
        dayData: Set<DayDataEntity>,
        nameInfo: NameInfo
    ) : this(email, passwordHash, refreshToken, persons, personRelations, dayData) {
        this.nameInfo = nameInfo
    }

    override fun toString(): String {
        return "User(id='$id', email='$email', passwordHash='$passwordHash', refreshToken=${refreshToken?.id}, persons=$persons, personRelations=$personRelations, dayData=$dayData, nameInfo=$nameInfo)"
    }

    override fun getAuthorities() = emptyList<GrantedAuthority>()

    override fun getPassword() = passwordHash

    override fun getUsername() = email

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true


}
