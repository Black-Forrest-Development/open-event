package de.sambalmueslie.openevent.core.api


import de.sambalmueslie.openevent.api.RegistrationAPI
import de.sambalmueslie.openevent.api.RegistrationAPI.Companion.PERMISSION_READ
import de.sambalmueslie.openevent.api.RegistrationAPI.Companion.PERMISSION_WRITE
import de.sambalmueslie.openevent.core.auth.checkPermission
import de.sambalmueslie.openevent.core.logic.RegistrationCrudService
import de.sambalmueslie.openevent.core.model.Registration
import de.sambalmueslie.openevent.core.model.RegistrationChangeRequest
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/registration")
@Tag(name = "Registration API")
class RegistrationController(private val service: RegistrationCrudService) : RegistrationAPI {

    @Get("/{id}")
    override fun get(auth: Authentication, id: Long): Registration? {
        return auth.checkPermission(PERMISSION_READ) { service.get(id) }
    }

    @Get()
    override fun getAll(auth: Authentication, pageable: Pageable): Page<Registration> {
        return auth.checkPermission(PERMISSION_READ) { service.getAll(pageable) }
    }

    @Post()
    override fun create(auth: Authentication, @Body request: RegistrationChangeRequest): Registration {
        return auth.checkPermission(PERMISSION_WRITE) { service.create(request) }
    }

    @Put("/{id}")
    override fun update(auth: Authentication, id: Long, @Body request: RegistrationChangeRequest): Registration {
        return auth.checkPermission(PERMISSION_WRITE) { service.update(id, request) }
    }

    @Delete("/{id}")
    override fun delete(auth: Authentication, id: Long): Registration? {
        return auth.checkPermission(PERMISSION_WRITE) { service.delete(id) }
    }

}
