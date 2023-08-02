package de.sambalmueslie.openevent.api

import de.sambalmueslie.openevent.core.model.Event
import de.sambalmueslie.openevent.core.model.EventChangeRequest
import de.sambalmueslie.openevent.core.model.PatchRequest
import io.micronaut.security.authentication.Authentication

interface EventAPI : CrudAPI<Long, Event, EventChangeRequest> {
    companion object {
        const val PERMISSION_READ = "openevent.event.read"
        const val PERMISSION_WRITE = "openevent.event.write"
    }

    fun setPublished(auth: Authentication, id: Long, value: PatchRequest<Boolean>): Event?

}
