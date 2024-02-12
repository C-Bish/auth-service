package org.bish.authservice.repo

import org.bish.authservice.model.Permission
import org.springframework.data.repository.CrudRepository

interface PermissionRepository : CrudRepository<Permission, Long> {
}