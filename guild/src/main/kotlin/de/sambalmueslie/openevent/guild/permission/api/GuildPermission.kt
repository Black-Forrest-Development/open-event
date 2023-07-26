package de.sambalmueslie.openevent.guild.permission.api

import com.fasterxml.jackson.annotation.JsonProperty
import de.sambalmueslie.openevent.common.crud.BusinessObject

data class GuildPermission(
    @JsonProperty("id")
    override val id: Long,
    @JsonProperty("name")
    val name: String,
    @JsonProperty("description")
    val description: String
) : BusinessObject<Long>
