package de.sambalmueslie.openevent.core.location.db

import de.sambalmueslie.openevent.common.Storage
import de.sambalmueslie.openevent.core.location.api.Location
import de.sambalmueslie.openevent.core.location.api.LocationChangeRequest
import de.sambalmueslie.openevent.core.logic.event.api.Event

interface LocationStorage : Storage<Long, Location, LocationChangeRequest> {
    fun create(request: LocationChangeRequest, event: Event): Location
    fun findByEvent(event: Event): Location?
    fun findByEventIds(eventIds: Set<Long>): List<Location>
}
