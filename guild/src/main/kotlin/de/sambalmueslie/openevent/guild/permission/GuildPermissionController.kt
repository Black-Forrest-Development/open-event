package de.sambalmueslie.openevent.guild.permission


import de.sambalmueslie.openevent.common.auth.checkPermission
import de.sambalmueslie.openevent.guild.permission.api.GuildPermission
import de.sambalmueslie.openevent.guild.permission.api.GuildPermissionAPI
import de.sambalmueslie.openevent.guild.permission.api.GuildPermissionAPI.Companion.PERMISSION_GUILD_PERMISSION_READ
import de.sambalmueslie.openevent.guild.permission.api.GuildPermissionAPI.Companion.PERMISSION_GUILD_PERMISSION_WRITE
import de.sambalmueslie.openevent.guild.permission.api.GuildPermissionChangeRequest
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.tags.Tag
import reactor.core.publisher.Mono

@Controller("/api/guild/permission")
@Tag(name = "Guild Permission API")
class GuildPermissionController(private val service: GuildPermissionService) : GuildPermissionAPI {
    @Get("/{id}")
    override fun get(auth: Authentication, @PathVariable id: Long): Mono<GuildPermission> = auth.checkPermission(PERMISSION_GUILD_PERMISSION_READ) {
        Mono.justOrEmpty(service.get(id))
    }

    @Get()
    override fun getAll(auth: Authentication, pageable: Pageable): Mono<Page<GuildPermission>> = auth.checkPermission(PERMISSION_GUILD_PERMISSION_READ) {
        Mono.just(service.getAll(pageable))
    }

    @Post()
    override fun create(auth: Authentication, @Body request: GuildPermissionChangeRequest): Mono<GuildPermission> = auth.checkPermission(PERMISSION_GUILD_PERMISSION_WRITE) {
        Mono.just(service.create(request))
    }

    @Put("/{id}")
    override fun update(auth: Authentication, @PathVariable id: Long, @Body request: GuildPermissionChangeRequest): Mono<GuildPermission> = auth.checkPermission(PERMISSION_GUILD_PERMISSION_WRITE) {
        Mono.just(service.update(id, request))
    }

    @Delete("/{id}")
    override fun delete(auth: Authentication, @PathVariable id: Long): Mono<GuildPermission> = auth.checkPermission(PERMISSION_GUILD_PERMISSION_WRITE) {
        Mono.justOrEmpty(service.delete(id))
    }
}
