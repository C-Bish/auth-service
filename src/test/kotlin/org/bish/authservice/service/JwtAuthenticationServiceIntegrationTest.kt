package org.bish.authservice.service

import io.micrometer.common.util.StringUtils.isNotEmpty
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class JwtAuthenticationServiceIntegrationTest(@Autowired val jwtAuthenticationService: JwtAuthenticationService) {

    @Test
    fun testGenerateToken() {
        val token: String = jwtAuthenticationService.generateToken("test")
        assertTrue(isNotEmpty(token))

        val username = jwtAuthenticationService.getUsername(token)
        assertTrue(username == "test")
    }

    @Test
    fun testValidateToken() {
        val token: String = jwtAuthenticationService.generateToken("test")
        assertTrue(isNotEmpty(token))

        val valid: Boolean = jwtAuthenticationService.validate(token)
        assertTrue(valid)
    }

}