package org.bish.authservice.controllers

import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api")
class UserController {

    @GetMapping("/public")
    fun getPublic(): String {
        return "This is a public endpoint"
    }

    @GetMapping("/private")
    fun getPrivate(): String {
        return "Hello, ${SecurityContextHolder.getContext().authentication.name}! This is a private endpoint"
    }
}