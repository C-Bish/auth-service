package org.bish.authservice.models

import jakarta.annotation.Generated
import jakarta.persistence.*

@Entity
@Table(name = "USERS")
class User(
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

    @ManyToMany()
    @JoinTable(
        name = "USERS_ROLES",
        joinColumns = [JoinColumn(name="USERS_ID", referencedColumnName="ID")],
        inverseJoinColumns = [JoinColumn(name="ROLES_ID", referencedColumnName="ID")]
    )
    val roles: Set<Role>? = mutableSetOf()
) {
}