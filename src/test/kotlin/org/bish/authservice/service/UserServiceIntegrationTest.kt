package org.bish.authservice.service

import org.bish.authservice.dto.UserRegistrationDTO
import org.bish.authservice.model.User
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.not
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class UserServiceIntegrationTest(@Autowired val userService: UserService) {

    @Test
    fun testLoadUserByUsername() {
        val user: UserDetails? = userService.loadUserByUsername("test")
        assertTrue(user != null)
        assertThat(user?.password, not("fakepassword"))
        println("Encoded password is: ${user?.password}")
    }

    @Test
    fun testRegisterUser() {
        val dto = UserRegistrationDTO("dummy", "D", "T", "dummy@mail.com", "password")
        val created: Boolean = userService.registerUser(dto)
        assertTrue(created)
    }

    @Test
    fun testRegisterDuplicateUser() {
        val dto = UserRegistrationDTO("test", "T", "T", "test@mail.com", "password")
        val created: Boolean = userService.registerUser(dto)
        assertFalse(created)
    }

    @Test
    fun testLogin() {
        val user = User(1, "test", "T", "T", "test@mail.com", "password")
        val token: String? = userService.login(user)
        assertTrue(token != null)
    }

}