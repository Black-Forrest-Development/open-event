package de.sambalmueslie.openevent.core.api


import de.sambalmueslie.openevent.api.EventAPI
import de.sambalmueslie.openevent.api.EventAPI.Companion.PERMISSION_READ
import de.sambalmueslie.openevent.api.EventAPI.Companion.PERMISSION_WRITE
import de.sambalmueslie.openevent.core.auth.checkPermission
import de.sambalmueslie.openevent.core.logic.EventCrudService
import de.sambalmueslie.openevent.core.model.*
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/event")
@Tag(name = "Event API")
class EventController(private val service: EventCrudService) : EventAPI {

    @Get("/{id}")
    override fun get(auth: Authentication, id: Long): Event? {
        return auth.checkPermission(PERMISSION_READ) { service.get(id) }
    }

    @Get("/{id}/info")
    override fun getInfo(auth: Authentication, id: Long): EventInfo? {
        return auth.checkPermission(PERMISSION_READ) { service.getInfo(id) }
    }

    @Get()
    override fun getAll(auth: Authentication, pageable: Pageable): Page<Event> {
        return auth.checkPermission(PERMISSION_READ) { service.getAll(pageable) }
    }

    @Get("/info")
    override fun getInfos(auth: Authentication, pageable: Pageable): Page<EventInfo> {
        return auth.checkPermission(PERMISSION_READ) { service.getInfos(pageable) }
    }

    @Post()
    override fun create(auth: Authentication, @Body request: EventChangeRequest): Event {
        return auth.checkPermission(PERMISSION_WRITE) { service.create(request, auth) }
    }

    @Put("/{id}")
    override fun update(auth: Authentication, id: Long, @Body request: EventChangeRequest): Event {
        return auth.checkPermission(PERMISSION_WRITE) { service.update(id, request) }
    }

    @Delete("/{id}")
    override fun delete(auth: Authentication, id: Long): Event? {
        return auth.checkPermission(PERMISSION_WRITE) { service.delete(id) }
    }

    @Put("/{id}/published")
    override fun setPublished(auth: Authentication, id: Long, @Body value: PatchRequest<Boolean>): Event? {
        return auth.checkPermission(PERMISSION_WRITE) { service.setPublished(id, value) }
    }

    @Get("/{id}/location")
    override fun getLocation(auth: Authentication, id: Long): Location? {
        return auth.checkPermission(PERMISSION_READ) { service.getLocation(id) }
    }


    @Get("/{id}/registration")
    override fun getRegistration(auth: Authentication, id: Long): Registration? {
        return auth.checkPermission(PERMISSION_READ) { service.getRegistration(id) }
    }

}
