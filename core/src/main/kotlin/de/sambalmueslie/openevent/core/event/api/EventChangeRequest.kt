package de.sambalmueslie.openevent.core.event.api

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import de.sambalmueslie.openevent.common.crud.BusinessObjectChangeRequest

@JsonIgnoreProperties(ignoreUnknown = true)
data class EventChangeRequest(
    @JsonProperty("title")
    val title: String = "",
) : BusinessObjectChangeRequest
