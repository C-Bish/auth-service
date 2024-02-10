package org.bish.authservice.service

import org.bish.authservice.dto.UserLoginDTO
import org.bish.authservice.dto.UserRegistrationDTO
import org.springframework.context.annotation.Bean
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class PasswordService {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    fun encode(user: UserRegistrationDTO) {
        user.password = passwordEncoder().encode(user.password)
    }

    fun encode(user: UserLoginDTO) {
        user.password = passwordEncoder().encode(user.password)
    }

}