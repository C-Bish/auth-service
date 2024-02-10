package org.bish.authservice.repo

import org.bish.authservice.models.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    /**
     * Find the user with the given username.
     *
     * @param name
     * @return
     */
    fun findByName(name: String): User

}