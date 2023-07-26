package de.sambalmueslie.openevent.core.event.api

import de.sambalmueslie.openevent.common.crud.AuthCrudAPI

interface EventAPI : AuthCrudAPI<Long, Event, EventChangeRequest> {
    companion object {
        const val PERMISSION_READ = "event.read"
        const val PERMISSION_WRITE = "event.write"
    }
}
