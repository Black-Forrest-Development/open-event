package de.sambalmueslie.openevent.guild.permission


import de.sambalmueslie.openevent.guild.permission.api.GuildPermissionScheme
import io.micronaut.http.annotation.Controller
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/guild/permission/scheme")
@Tag(name = "Guild Permission Scheme API")
class GuildPermissionSchemeController(private val service: GuildPermissionScheme) {


}
