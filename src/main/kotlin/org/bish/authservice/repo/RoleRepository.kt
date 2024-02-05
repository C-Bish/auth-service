package org.bish.authservice.repo

import org.bish.authservice.models.Role
import org.bish.authservice.models.User
import org.springframework.data.repository.CrudRepository

interface RoleRepository : CrudRepository<Role, Long> {
}