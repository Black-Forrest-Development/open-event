package de.sambalmueslie.openevent.user


import de.sambalmueslie.openevent.user.api.UserChangeRequest
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/user")
@Tag(name = "User API")
class UserController(private val service: UserService) {

    @Get("/{userId}")
    fun get(userId: Long) = service.get(userId)

    @Get()
    fun getAll(pageable: Pageable) = service.getAll(pageable)

    @Post()
    fun create(@Body request: UserChangeRequest) = service.create(request)

    @Put("/{userId}")
    fun update(userId: Long, @Body request: UserChangeRequest) = service.update(userId, request)

    @Delete("/{userId}")
    fun delete(userId: Long) = service.delete(userId)

}
