package de.sambalmueslie.openevent.core.user

import de.sambalmueslie.openevent.common.time.TimeProvider
import de.sambalmueslie.openevent.core.user.api.User
import de.sambalmueslie.openevent.core.user.api.UserChangeListener
import de.sambalmueslie.openevent.core.user.api.UserChangeRequest
import de.sambalmueslie.openevent.core.user.api.UserType
import io.micronaut.data.model.Pageable
import io.micronaut.test.annotation.MockBean
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@MicronautTest
internal class UserServiceTest {


    @Inject
    lateinit var service: UserService

    private val timeProvider = mockk<TimeProvider>()

    @MockBean(TimeProvider::class)
    fun timeProvider() = timeProvider

    private val listener = mockk<UserChangeListener>()

    @Test
    fun checkCrud() {
        val now = LocalDateTime.of(2022, 10, 8, 20, 15, 0)
        every { timeProvider.now() } returns now

        service.register(listener)

        // create
        val createRequest = UserChangeRequest("externalId", UserType.IDP, "userName", "firstName", "lastName", "email", "mobile", "phone")
        var result = service.create(createRequest)

        var reference = User(
            1, createRequest.externalId, createRequest.type, createRequest.userName, createRequest.firstName, createRequest.lastName, createRequest.email, createRequest.mobile, createRequest.phone
        )
        assertEquals(reference, result)
        verify { listener.handleCreated(reference) }
        verify { listener.hashCode() }
        confirmVerified(listener)

        // read
        assertEquals(reference, service.get(reference.id))
        assertEquals(listOf(reference), service.getAll(Pageable.from(0)).content)

        // update
        val updateRequest = UserChangeRequest("update-externalId", UserType.MANUAL, "update-userName", "update-firstName", "update-lastName", "update-email", "update-mobile", "update-phone")
        result = service.update(reference.id, updateRequest)

        reference = User(
            1,
            updateRequest.externalId,
            updateRequest.type,
            updateRequest.userName,
            updateRequest.firstName,
            updateRequest.lastName,
            updateRequest.email,
            updateRequest.mobile,
            updateRequest.phone
        )
        assertEquals(reference, result)
        verify { listener.handleUpdated(reference) }
        verify { listener.hashCode() }
        confirmVerified(listener)

        // delete
        service.delete(reference.id)

        // read empty
        assertEquals(null, service.get(reference.id))
        assertEquals(emptyList<User>(), service.getAll(Pageable.from(0)).content)
    }

}
