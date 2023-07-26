package de.sambalmueslie.openevent.guild.permission.api

import de.sambalmueslie.openevent.common.crud.AuthCrudAPI

interface GuildPermissionSchemeAPI : AuthCrudAPI<Long, GuildPermissionScheme, GuildPermissionSchemeChangeRequest> {
    companion object {
        const val PERMISSION_GUILD_PERMISSION_SCHEME_READ = "guild.permission.scheme.read"
        const val PERMISSION_GUILD_PERMISSION_SCHEME_WRITE = "guild.permission.scheme.write"
    }
}
