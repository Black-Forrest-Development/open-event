package de.sambalmueslie.openevent.gateway.external.event

import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/external/event")
@Tag(name = "Public Event API")
@Secured(SecurityRule.IS_ANONYMOUS)
class EventController(
    private val service: ExternalEventService
) {

    @Get("{id}")
    fun get(id: String): PublicEvent? {
        return service.getPublicEvent(id)
    }

}