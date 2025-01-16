package de.sambalmueslie.openevent.gateway.app.event

import de.sambalmueslie.openevent.core.event.EventCrudService
import de.sambalmueslie.openevent.infrastructure.audit.AuditService
import io.micronaut.http.annotation.Controller
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/app/event")
@Tag(name = "APP Event API")
class EventController(
    private val service: EventCrudService,
    audit: AuditService,
) {
    companion object {
        private const val PERMISSION_READ = "event.read"
        private const val PERMISSION_WRITE = "event.write"
    }


    private val logger = audit.getLogger("APP Event API")
}