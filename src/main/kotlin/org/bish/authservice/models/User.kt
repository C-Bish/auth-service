package org.bish.authservice.models

import jakarta.annotation.Generated
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Entity
@Table(name = "USERS")
class User (
    @Id
    @Generated
    val id: Long? = null,

    @Column(name = "NAME")
    val name: String? = null,

    @Column(name = "FIRST_NAME")
    val firstName: String? = null,

    @Column(name = "LAST_NAME")
    val lastName: String? = null,

    @Column(name = "EMAIL")
    val email: String? = null,

    @Column(name = "PASSWORD")
    val password: String? = null,

    @ManyToMany()
    @JoinTable(
        name = "USERS_ROLES",
        joinColumns = [JoinColumn(name="USERS_ID", referencedColumnName="ID")],
        inverseJoinColumns = [JoinColumn(name="ROLES_ID", referencedColumnName="ID")]
    )
    val roles: Set<Role>? = mutableSetOf()
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        TODO("Not yet implemented")
    }

    override fun getPassword(): String {
        TODO("Not yet implemented")
    }

    override fun getUsername(): String {
        TODO("Not yet implemented")
    }

    override fun isAccountNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isAccountNonLocked(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isCredentialsNonExpired(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isEnabled(): Boolean {
        TODO("Not yet implemented")
    }

}