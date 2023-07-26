package de.sambalmueslie.openevent.core.event


import de.sambalmueslie.openevent.common.auth.checkPermission
import de.sambalmueslie.openevent.core.event.api.Event
import de.sambalmueslie.openevent.core.event.api.EventAPI
import de.sambalmueslie.openevent.core.event.api.EventChangeRequest
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.tags.Tag
import reactor.core.publisher.Mono

@Controller("/api/event")
@Tag(name = "Event API")
class EventController(private val service: EventService) : EventAPI {
    @Get("/{id}")
    override fun get(auth: Authentication, @PathVariable id: Long): Mono<Event> = auth.checkPermission(EventAPI.PERMISSION_READ) {
        Mono.justOrEmpty(service.get(id))
    }
    @Get()
    override fun getAll(auth: Authentication, pageable: Pageable): Mono<Page<Event>> = auth.checkPermission(EventAPI.PERMISSION_READ) {
        Mono.just(service.getAll(pageable))
    }
    @Post()
    override fun create(auth: Authentication, @Body request: EventChangeRequest): Mono<Event> = auth.checkPermission(EventAPI.PERMISSION_WRITE) {
        Mono.just(service.create(request))
    }
    @Put("/{id}")
    override fun update(auth: Authentication, @PathVariable id: Long, @Body request: EventChangeRequest): Mono<Event> = auth.checkPermission(EventAPI.PERMISSION_WRITE) {
        Mono.just(service.update(id, request))
    }
    @Delete("/{id}")
    override fun delete(auth: Authentication, @PathVariable id: Long): Mono<Event> = auth.checkPermission(EventAPI.PERMISSION_WRITE) {
        Mono.justOrEmpty(service.delete(id))
    }

}
