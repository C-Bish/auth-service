package org.bish.authservice.models

import jakarta.annotation.Generated
import jakarta.persistence.*

@Entity
@Table(name = "ROLES")
class Role(
    @Id
    @Generated
    val id: Long? = null,

    @Column(name = "NAME")
    val name: String? = null,

    @ManyToMany()
    @JoinTable(
        name = "ROLE_PERMISSIONS",
        joinColumns = [JoinColumn(name="ROLES_ID", referencedColumnName="ID")],
        inverseJoinColumns = [JoinColumn(name="PERMISSION_ID", referencedColumnName="ID")]
    )
    val permissions: Set<Permission>? = mutableSetOf()
) {
}