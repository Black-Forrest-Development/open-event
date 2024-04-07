package de.sambalmueslie.openevent.core.logic.location.db

import de.sambalmueslie.openevent.core.Storage
import de.sambalmueslie.openevent.core.logic.event.api.Event
import de.sambalmueslie.openevent.core.logic.location.api.Location
import de.sambalmueslie.openevent.core.logic.location.api.LocationChangeRequest

interface LocationStorage : Storage<Long, Location, LocationChangeRequest> {
    fun create(request: LocationChangeRequest, event: Event): Location
    fun findByEvent(event: Event): Location?
    fun findByEventIds(eventIds: Set<Long>): List<Location>
}
