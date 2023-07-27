package de.sambalmueslie.openevent.core.logic


import de.sambalmueslie.openevent.core.BaseCrudService
import de.sambalmueslie.openevent.core.model.Event
import de.sambalmueslie.openevent.core.model.Location
import de.sambalmueslie.openevent.core.model.LocationChangeRequest
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class LocationCrudService(
    private val storage: LocationStorage
) : BaseCrudService<Long, Location, LocationChangeRequest>(storage, logger) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(LocationCrudService::class.java)
    }

    fun create(event: Event, request: LocationChangeRequest): Location {
        return storage.create(request, event)
    }

    fun findByEvent(event: Event): Location? {
        return storage.findByEvent(event)
    }

    fun updateByEvent(event: Event, request: LocationChangeRequest): Location {
        val existing = storage.findByEvent(event)
        return if (existing != null) {
            storage.update(existing.id, request)
        } else {
            storage.create(request, event)
        }
    }

    fun deleteByEvent(event: Event): Location? {
        val existing = storage.findByEvent(event) ?: return null
        return storage.delete(existing.id)
    }


}
