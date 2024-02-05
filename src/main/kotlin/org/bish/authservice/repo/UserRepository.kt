package org.bish.authservice.repo

import org.bish.authservice.models.User
import org.springframework.data.repository.CrudRepository

interface UserRepository : CrudRepository<User, Long> {
}