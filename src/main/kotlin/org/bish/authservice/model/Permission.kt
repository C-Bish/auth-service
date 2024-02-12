package org.bish.authservice.model

import jakarta.annotation.Generated
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "PERMISSION")
class Permission(
    @Id
    @Generated
    val id: Long? = null,

    @Column(name = "PERMISSION_TYPE")
    val type: String? = null,
) {
}