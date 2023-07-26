package de.sambalmueslie.openevent.core.event.api

import com.fasterxml.jackson.annotation.JsonProperty
import de.sambalmueslie.openevent.common.crud.BusinessObject

data class Event(
    @JsonProperty("id")
    override val id: Long,
    @JsonProperty("title")
    val title: String = "",
) : BusinessObject<Long>
