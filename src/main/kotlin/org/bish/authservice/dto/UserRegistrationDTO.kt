package org.bish.authservice.dto

import org.bish.authservice.model.User

class UserRegistrationDTO(
    val name: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    var password: String
) {

    fun transform(): User {
        return User(null, name, firstName, lastName, email, password)
    }

}