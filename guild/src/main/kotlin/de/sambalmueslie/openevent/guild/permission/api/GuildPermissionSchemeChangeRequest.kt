package de.sambalmueslie.openevent.guild.permission.api

import com.fasterxml.jackson.annotation.JsonProperty
import de.sambalmueslie.openevent.common.crud.BusinessObject
import de.sambalmueslie.openevent.common.crud.BusinessObjectChangeRequest

data class GuildPermissionSchemeChangeRequest(
    @JsonProperty("name")
    val name: String,
    @JsonProperty("description")
    val description: String
): BusinessObjectChangeRequest
