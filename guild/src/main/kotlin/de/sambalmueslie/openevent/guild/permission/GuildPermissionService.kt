package de.sambalmueslie.openevent.guild.permission


import de.sambalmueslie.openevent.common.crud.GenericCrudService
import de.sambalmueslie.openevent.common.error.InvalidRequestException
import de.sambalmueslie.openevent.guild.db.GuildPermissionData
import de.sambalmueslie.openevent.guild.db.GuildPermissionRepository
import de.sambalmueslie.openevent.guild.permission.api.GuildPermission
import de.sambalmueslie.openevent.guild.permission.api.GuildPermissionChangeRequest
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class GuildPermissionService(
    private val repository: GuildPermissionRepository
) : GenericCrudService<Long, GuildPermission, GuildPermissionChangeRequest, GuildPermissionData>(repository, logger) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(GuildPermissionService::class.java)
    }

    override fun createData(request: GuildPermissionChangeRequest): GuildPermissionData {
        TODO("Not yet implemented")
    }

    override fun updateData(data: GuildPermissionData, request: GuildPermissionChangeRequest): GuildPermissionData {
        TODO("Not yet implemented")
    }

    override fun isValid(request: GuildPermissionChangeRequest) {
        if (request.name.isBlank()) throw InvalidRequestException("Guild Permission name could not be blank")
    }


}
