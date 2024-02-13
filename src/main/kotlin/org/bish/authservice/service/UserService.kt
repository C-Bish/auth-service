package org.bish.authservice.service

import io.jsonwebtoken.JwtException
import org.bish.authservice.dto.UserRegistrationDTO
import org.bish.authservice.model.User
import org.bish.authservice.repo.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class UserService(
    @Autowired
    val userRepository: UserRepository,
    val passwordService: PasswordService,
    val jwtAuthenticationService: JwtAuthenticationService
): UserDetailsService {

    private final val log: Logger = LoggerFactory.getLogger(this.javaClass)

    /**
     * Loads a single User with a username.
     *
     * @param username
     * @return
     */
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(username: String): UserDetails? {
        val user = userRepository.findByName(username) ?: throw UsernameNotFoundException("Cannot find user: $username")
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
            // Perform password hashing
            passwordService.encode(userDTO)

            userRepository.save(userDTO.transform())
            return true
        }

        // User cannot be registered as username is taken.
        log.warn("User registration failed due to duplicate username.")
        return false
    }

    /**
     * Performs login process for a user.
     *
     * @param user
     * @return
     */
    fun login(user: User) : String? {
        try {
            return jwtAuthenticationService.generateToken(user.name)
        } catch (ex: JwtException) {
            log.error("Error generating token for user: ${user.name}.")
            return null
        }
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