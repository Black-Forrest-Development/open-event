package de.sambalmueslie.openevent.core.search.api

import io.micronaut.data.model.Page

data class EventSearchResponse(
    override val result: Page<EventSearchEntry>
) : SearchResponse<EventSearchEntry>
