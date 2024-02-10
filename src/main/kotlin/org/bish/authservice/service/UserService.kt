package org.bish.authservice.service

import org.bish.authservice.models.User
import org.bish.authservice.repo.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails {
        val user : User = userRepository.findByName(username)
        if (user.name == null) {
            throw UsernameNotFoundException("User not found")
        }
        return user
    }
}