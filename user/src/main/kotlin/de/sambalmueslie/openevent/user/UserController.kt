package de.sambalmueslie.openevent.user


import de.sambalmueslie.openevent.user.api.UserChangeRequest
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/user")
@Tag(name = "User API")
class UserController(private val service: UserService) {

    @Get("/{userId}")
    fun get(auth: Authentication, userId: Long) = service.get(auth, userId)

    @Get()
    fun getAll(auth: Authentication, pageable: Pageable) = service.getAll(auth, pageable)

    @Post()
    fun create(auth: Authentication, @Body request: UserChangeRequest) = service.create(auth, request)

    @Put("/{userId}")
    fun update(auth: Authentication, userId: Long, @Body request: UserChangeRequest) = service.update(auth, userId, request)

    @Delete("/{userId}")
    fun delete(auth: Authentication, userId: Long) = service.delete(auth, userId)

}
