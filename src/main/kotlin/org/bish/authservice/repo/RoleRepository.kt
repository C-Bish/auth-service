package org.bish.authservice.repo

import org.bish.authservice.model.Role
import org.springframework.data.repository.CrudRepository

interface RoleRepository : CrudRepository<Role, Long> {
}