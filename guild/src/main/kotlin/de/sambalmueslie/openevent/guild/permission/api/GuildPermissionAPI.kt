package de.sambalmueslie.openevent.guild.permission.api

import de.sambalmueslie.openevent.common.crud.AuthCrudAPI

interface GuildPermissionAPI : AuthCrudAPI<Long, GuildPermission, GuildPermissionChangeRequest> {
    companion object {
        const val PERMISSION_GUILD_PERMISSION_READ = "guild.permission.read"
        const val PERMISSION_GUILD_PERMISSION_WRITE = "guild.permission.write"
    }
}
