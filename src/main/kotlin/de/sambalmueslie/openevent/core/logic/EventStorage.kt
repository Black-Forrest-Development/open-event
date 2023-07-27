package de.sambalmueslie.openevent.core.logic

import de.sambalmueslie.openevent.core.Storage
import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.core.model.Event
import de.sambalmueslie.openevent.core.model.EventChangeRequest

interface EventStorage : Storage<Long, Event, EventChangeRequest> {
    fun create(request: EventChangeRequest, owner: Account): Event
}
