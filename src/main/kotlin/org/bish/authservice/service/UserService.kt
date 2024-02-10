package org.bish.authservice.service

import org.bish.authservice.dto.UserLoginDTO
import org.bish.authservice.dto.UserRegistrationDTO
import org.bish.authservice.repo.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserService(@Autowired val userRepository: UserRepository): UserDetailsService {

    /**
     * Loads a single User with a username.
     *
     * @param username
     * @return
     */
    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByName(username) ?: throw UsernameNotFoundException("User not found")
        return user
    }

    /**
     * Performs registration for a user.
     *
     * @param userDTO
     * @return
     */
    fun registerUser(userDTO: UserRegistrationDTO) : Boolean {
        if (isUsernameAvailable(userDTO.name, userDTO.email)) {
            userRepository.save(userDTO.transform())
            return true
        }

        // User cannot be registered as username is taken.
        return false
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
     * @return
     */
    fun logout() : Boolean {
        return true
    }

    private fun isUsernameAvailable(username: String, email: String) : Boolean {

        // Search by username
        var user = userRepository.findByName(username)
        if (user != null) {
            return false
        }

        // Search by email
        user = userRepository.findByEmail(email)
        return user == null
    }

}