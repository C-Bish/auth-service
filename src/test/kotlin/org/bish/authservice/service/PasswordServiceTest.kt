package org.bish.authservice.service

import org.bish.authservice.dto.UserLoginDTO
import org.bish.authservice.dto.UserRegistrationDTO
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.crypto.password.PasswordEncoder

@SpringBootTest
class PasswordServiceTest(@Autowired val passwordService: PasswordService, @Autowired val encoder: PasswordEncoder) {

    @Test
    fun testEncodeRegistration() {
        val dto = UserRegistrationDTO("dummy", "D", "U", "du@mail.com", "password")
        passwordService.encode(dto)
        assertThat(dto.password, not("password"))
        assertTrue(encoder.matches("password", dto.password))
    }

    @Test
    fun testEncodeLogin() {
        val dto = UserLoginDTO("dummy", "password")
        passwordService.encode(dto)
        assertThat(dto.password, not("password"))
        assertTrue(encoder.matches("password", dto.password))
    }

}