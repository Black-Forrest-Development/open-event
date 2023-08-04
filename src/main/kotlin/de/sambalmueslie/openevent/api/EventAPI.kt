package de.sambalmueslie.openevent.api

import de.sambalmueslie.openevent.core.model.*
import io.micronaut.security.authentication.Authentication

interface EventAPI : CrudAPI<Long, Event, EventChangeRequest> {
    companion object {
        const val PERMISSION_READ = "openevent.event.read"
        const val PERMISSION_WRITE = "openevent.event.write"
    }

    fun setPublished(auth: Authentication, id: Long, value: PatchRequest<Boolean>): Event?
    fun getInfo(auth: Authentication, id: Long): EventInfo?
    fun getLocation(auth: Authentication, id: Long): Location?
    fun getRegistration(auth: Authentication, id: Long): Registration?
}
