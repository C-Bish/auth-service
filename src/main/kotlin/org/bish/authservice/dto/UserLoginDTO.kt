package org.bish.authservice.dto

class UserLoginDTO(
    val name: String,
    val email: String,
    var password: String
) {
}