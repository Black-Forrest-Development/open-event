package de.sambalmueslie.openevent.api

import de.sambalmueslie.openevent.core.search.api.EventSearchRequest
import de.sambalmueslie.openevent.core.search.api.EventSearchResponse
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication

interface SearchAPI {

    companion object {
        const val PERMISSION_ADMIN = "openevent.search.admin"
    }

    fun searchEvents(auth: Authentication, request: EventSearchRequest, pageable: Pageable): EventSearchResponse
}