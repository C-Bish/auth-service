package org.bish.authservice.repo

import org.bish.authservice.model.User
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserRepositoryTest(@Autowired val repository: UserRepository) {

    @Test
    fun testFindByName() {
        var user: User? = repository.findByName("test")
        assertTrue(user != null)

        user = repository.findByName("doesn't exist")
        assertTrue(user == null)
    }

    @Test
    fun testFindByEmail() {
        var user: User? = repository.findByEmail("test@mail.com")
        assertTrue(user != null)

        user = repository.findByEmail("doesn't exist")
        assertTrue(user == null)
    }

}