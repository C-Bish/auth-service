package org.bish.authservice.models

import jakarta.annotation.Generated
import jakarta.persistence.*
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
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

    @Column(name = "IS_EXPIRED")
    val isExpired: Boolean = false,

    @Column(name = "IS_LOCKED")
    val isLocked: Boolean = false,

    private val isCredentialsExpired: Boolean = false,

    private val isEnabled: Boolean = true,

    @ManyToMany()
    @JoinTable(
        name = "USERS_ROLES",
        joinColumns = [JoinColumn(name="USERS_ID", referencedColumnName="ID")],
        inverseJoinColumns = [JoinColumn(name="ROLES_ID", referencedColumnName="ID")]
    )
    val roles: Set<Role>? = mutableSetOf()
) : UserDetails {
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> {
        val grantedAuthorities: MutableSet<GrantedAuthority> = mutableSetOf()
        if (roles != null) {
            for (role in roles) {
                grantedAuthorities.add(SimpleGrantedAuthority("ROLE_" + role.name))
            }
        }
        return grantedAuthorities
    }

    override fun getPassword(): String? {
        return password
    }

    override fun getUsername(): String? {
        return name
    }

    override fun isAccountNonExpired(): Boolean {
        return isExpired
    }

    override fun isAccountNonLocked(): Boolean {
        return !isLocked
    }

    override fun isCredentialsNonExpired(): Boolean {
        return !isCredentialsExpired
    }

    override fun isEnabled(): Boolean {
        return isEnabled
    }

}