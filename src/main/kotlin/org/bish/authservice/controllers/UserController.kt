package org.bish.authservice.controllers

import org.bish.authservice.dto.UserLoginDTO
import org.bish.authservice.dto.UserRegistrationDTO
import org.bish.authservice.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@RequestMapping("/api")
class UserController(val userService: UserService) {

    @PostMapping("/register")
    fun registerUser(@RequestBody userDTO: UserRegistrationDTO) : ResponseEntity<*> {
        userService.registerUser(userDTO)
        return ResponseEntity.ok("User registered successfully")
    }

    @PostMapping("/login")
    fun login(@RequestBody userDTO: UserLoginDTO) : ResponseEntity<*> {
        // Perform authentication logic
        userService.login(userDTO)
        return ResponseEntity.ok("Login successful");
    }

    @PostMapping("/logout")
    fun logout() : ResponseEntity<*> {
        // Perform logout logic, including token/session invalidation
        return ResponseEntity.ok("Logout successful")
    }

}