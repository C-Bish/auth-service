package org.bish.authservice.controller

import org.bish.authservice.dto.UserLoginDTO
import org.bish.authservice.dto.UserRegistrationDTO
import org.bish.authservice.model.User
import org.bish.authservice.service.UserService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController(
    val userService: UserService,
    val authenticationManager: AuthenticationManager
) {

    private final val log: Logger = LoggerFactory.getLogger(this.javaClass)

    @PostMapping("register")
    fun registerUser(@RequestBody userDTO: UserRegistrationDTO) : ResponseEntity<*> {
        val registered = userService.registerUser(userDTO)
        if (registered) {
            log.info("Successfully registered user: ${userDTO.name}.")
            return ResponseEntity.ok("User registered successfully.")
        }
        log.error("Failed to register user: ${userDTO.name}.")
        return ResponseEntity.badRequest().body("User could not be registered.")
    }

    @PostMapping("login")
    fun login(@RequestBody userDTO: UserLoginDTO) : ResponseEntity<*> {
        // Perform authentication logic
        val authenticate: Authentication = authenticationManager
            .authenticate(
                UsernamePasswordAuthenticationToken(
                    userDTO.username, userDTO.password
                )
            )

        val accessToken = userService.login(authenticate.principal as User)
        if (accessToken != null) {
            log.info("Login successful for user: ${userDTO.username}.")
            return ResponseEntity.ok()
                .header(
                    HttpHeaders.AUTHORIZATION,
                    accessToken
                )
                .body("Login successful")
        }
        log.error("Failed login attempt for user: ${userDTO.username}.")
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed")
    }

    @PostMapping("logout")
    fun logout() : ResponseEntity<*> {
        // Perform logout logic, including token/session invalidation
        userService.logout()
        return ResponseEntity.ok("Logout successful")
    }

}