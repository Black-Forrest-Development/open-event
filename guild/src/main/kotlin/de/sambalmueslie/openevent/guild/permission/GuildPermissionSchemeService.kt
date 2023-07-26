package de.sambalmueslie.openevent.guild.permission


import de.sambalmueslie.openevent.guild.db.GuildPermissionSchemeRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class GuildPermissionSchemeService(private val repository: GuildPermissionSchemeRepository) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(GuildPermissionSchemeService::class.java)
    }


}
