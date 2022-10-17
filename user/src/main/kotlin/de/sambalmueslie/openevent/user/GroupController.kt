package de.sambalmueslie.openevent.user


import de.sambalmueslie.openevent.common.auth.checkPermission
import de.sambalmueslie.openevent.user.api.Group
import de.sambalmueslie.openevent.user.api.GroupChangeRequest
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.tags.Tag
import reactor.core.publisher.Mono


@Controller("/api/group")
@Tag(name = "Group API")
class GroupController(private val service: GroupService) {

    companion object {
        const val PERMISSION_GROUP_READ = "group.read"
        const val PERMISSION_GROUP_WRITE = "group.write"
    }

    @Get("/{groupId}")
    fun get(auth: Authentication, @PathVariable groupId: Long): Mono<Group> = auth.checkPermission(PERMISSION_GROUP_READ) {
        Mono.justOrEmpty(service.get(groupId))
    }

    @Get()
    fun getAll(auth: Authentication, pageable: Pageable): Mono<Page<Group>> = auth.checkPermission(PERMISSION_GROUP_READ) {
        Mono.just(service.getAll(pageable))
    }

    @Post()
    fun create(auth: Authentication, @Body request: GroupChangeRequest): Mono<Group> = auth.checkPermission(PERMISSION_GROUP_WRITE) {
        Mono.just(service.create(request))
    }

    @Put("/{groupId}")
    fun update(auth: Authentication, @PathVariable groupId: Long, @Body request: GroupChangeRequest): Mono<Group> = auth.checkPermission(PERMISSION_GROUP_WRITE) {
        Mono.just(service.update(groupId, request))
    }

    @Delete("/{groupId}")
    fun delete(auth: Authentication, @PathVariable groupId: Long): Mono<Group> = auth.checkPermission(PERMISSION_GROUP_WRITE) {
        Mono.justOrEmpty(service.delete(groupId))
    }
}
