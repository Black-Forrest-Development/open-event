package de.sambalmueslie.openevent.core.logic

import de.sambalmueslie.openevent.TimeBasedTest
import de.sambalmueslie.openevent.core.model.*
import io.micronaut.data.model.Pageable
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@MicronautTest
class EventCrudServiceTest : TimeBasedTest() {
    @Inject
    lateinit var accountService: AccountCrudService

    @Inject
    lateinit var service: EventCrudService

    @Inject
    lateinit var locationService: LocationCrudService

    @Inject
    lateinit var registrationService: RegistrationCrudService

    @Test
    fun eventCrud() {
        val owner = accountService.create(AccountChangeRequest("name", "first-name", "last-name", "email", ""))

        val start = LocalDateTime.of(2023, 10, 1, 20, 15)
        val finish = LocalDateTime.of(2023, 10, 1, 22, 15)
        val locationRequest = LocationChangeRequest("street", "nr", "zip", "city", "country", "info", 1.0, 2.0, 3)
        val registrationRequest = RegistrationChangeRequest(10, true, true, false)
        val request = EventChangeRequest(
            start, finish,
            "title",
            "short",
            "long",
            "image",
            "icon",
            locationRequest, registrationRequest
        )
        var event = service.create(request, owner)
        validate(request, event)

        assertEquals(event, service.get(event.id))
        assertEquals(listOf(event), service.getAll(Pageable.from(0)).content)

        val locationUpdate = LocationChangeRequest(
            "street-update",
            "nr-update",
            "zip-update",
            "city-update",
            "country-update",
            "info-update",
            10.0,
            20.0,
            30
        )
        val registrationUpdate = RegistrationChangeRequest(100, false, false, true)
        val update = EventChangeRequest(
            start.plusDays(1), finish.plusDays(1),
            "title-update",
            "short-update",
            "long-update",
            "image-update",
            "icon-update",
            locationUpdate, registrationUpdate
        )

        event = service.update(event.id, update)
        validate(update, event)

        service.delete(event.id)
        assertEquals(emptyList<Event>(), service.getAll(Pageable.from(0)).content)
        assertEquals(emptyList<Location>(), locationService.getAll(Pageable.from(0)).content)
        assertEquals(emptyList<Registration>(), registrationService.getAll(Pageable.from(0)).content)

    }

    private fun validate(request: EventChangeRequest, event: Event) {
        assertEquals(request.start, event.start)
        assertEquals(request.finish, event.finish)
        assertEquals(request.title, event.title)
        assertEquals(request.shortText, event.shortText)
        assertEquals(request.longText, event.longText)
        assertEquals(request.imageUrl, event.imageUrl)
        assertEquals(request.iconUrl, event.iconUrl)

        val locationRequest = request.location
        if (locationRequest != null) {
            val location = locationService.findByEvent(event)
            assertNotNull(location)
            assertEquals(locationRequest.street, location!!.street)
            assertEquals(locationRequest.streetNumber, location.streetNumber)
            assertEquals(locationRequest.zip, location.zip)
            assertEquals(locationRequest.city, location.city)
            assertEquals(locationRequest.country, location.country)
            assertEquals(locationRequest.additionalInfo, location.additionalInfo)
            assertEquals(locationRequest.lat, location.lat)
            assertEquals(locationRequest.lon, location.lon)
            assertEquals(locationRequest.size, location.size)
        }

        val registrationRequest = request.registration
        val registration = registrationService.findByEvent(event)
        assertNotNull(registration)
        assertEquals(registrationRequest.maxGuestAmount, registration!!.maxGuestAmount)
        assertEquals(registrationRequest.interestedAllowed, registration.interestedAllowed)
        assertEquals(registrationRequest.interestedAllowed, registration.interestedAllowed)
        assertEquals(registrationRequest.ticketsEnabled, registration.ticketsEnabled)
    }

}
