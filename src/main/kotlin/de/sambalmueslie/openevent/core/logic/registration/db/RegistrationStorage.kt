package de.sambalmueslie.openevent.core.logic.registration.db

import de.sambalmueslie.openevent.core.Storage
import de.sambalmueslie.openevent.core.logic.event.api.Event
import de.sambalmueslie.openevent.core.logic.registration.api.Registration
import de.sambalmueslie.openevent.core.logic.registration.api.RegistrationChangeRequest

interface RegistrationStorage : Storage<Long, Registration, RegistrationChangeRequest> {
    fun create(request: RegistrationChangeRequest, event: Event): Registration
    fun findByEvent(event: Event): Registration?
    fun findByEventIds(eventIds: Set<Long>): List<Registration>
}
