package org.bish.authservice.controllers

import org.bish.authservice.dto.UserLoginDTO
import org.bish.authservice.dto.UserRegistrationDTO
import org.bish.authservice.models.User
import org.bish.authservice.service.UserService
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

    @PostMapping("register")
    fun registerUser(@RequestBody userDTO: UserRegistrationDTO) : ResponseEntity<*> {
        val registered = userService.registerUser(userDTO)
        if (registered) {
            return ResponseEntity.ok("User registered successfully.")
        }
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
            return ResponseEntity.ok()
                .header(
                    HttpHeaders.AUTHORIZATION,
                    accessToken
                )
                .body("Login successful")
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed")
    }

    @PostMapping("logout")
    fun logout() : ResponseEntity<*> {
        // Perform logout logic, including token/session invalidation
        userService.logout()
        return ResponseEntity.ok("Logout successful")
    }

}