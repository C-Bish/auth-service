package org.bish.authservice.repo

import org.bish.authservice.model.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {

    /**
     * Find the user with the given username.
     *
     * @param name
     * @return
     */
    fun findByName(name: String): User?

    /**
     * Find the user with the given email.
     *
     * @param email
     * @return
     */
    fun findByEmail(email: String): User?

}