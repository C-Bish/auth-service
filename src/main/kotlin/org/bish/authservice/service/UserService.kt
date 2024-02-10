package org.bish.authservice.service

import org.bish.authservice.dto.UserLoginDTO
import org.bish.authservice.dto.UserRegistrationDTO
import org.bish.authservice.models.User
import org.bish.authservice.repo.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(val userRepository: UserRepository): UserDetailsService {

    /**
     * Loads a single User with a username.
     *
     * @param username
     * @return
     */
    override fun loadUserByUsername(username: String): UserDetails {
        val user : User = userRepository.findByName(username)
        if (user.name == null) {
            throw UsernameNotFoundException("User not found")
        }
        return user
    }

    /**
     * Performs registration for a user.
     *
     * @param userDTO
     * @return
     */
    fun registerUser(userDTO: UserRegistrationDTO) : Boolean {
        return true
    }

    /**
     * Performs login process for a user.
     *
     * @param userDTO
     * @return
     */
    fun login(userDTO: UserLoginDTO) : Boolean {
        return true
    }

    /**
     * Performs logout process for a user.
     *
     * @param username
     * @return
     */
    fun logout(username: String) : Boolean {
        return true
    }

}