package org.bish.authservice.controller

import org.bish.authservice.dto.UserRegistrationDTO
import org.bish.authservice.model.User
import org.bish.authservice.service.UserService
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.stubbing.OngoingStubbing
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpEntity
import org.springframework.http.HttpMethod.POST
import org.springframework.http.ResponseEntity




@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest(

    @Autowired
    val restTemplate: TestRestTemplate
) {

    val service = mock<UserService>()

    private val dto = UserRegistrationDTO("dummy", "D", "U", "du@mail.com", "password")
    private val user = User(1, dto.name, dto.firstName, dto.lastName, dto.email, dto.password)

    @Test
    fun registerUser() {
        whenever(service.registerUser(dto)).thenReturn(true)

        val response: ResponseEntity<Boolean> = restTemplate.exchange(
            "/api/register",
            POST,
            HttpEntity<Any?>(true),
            Boolean::class.java
        )

        assertTrue(response.statusCode.value() == 201)
        response.body?.let { assertTrue(it) }
    }

    // To avoid having to use backticks for "when"
    fun <T> whenever(methodCall: T): OngoingStubbing<T> =
        Mockito.`when`(methodCall)

}