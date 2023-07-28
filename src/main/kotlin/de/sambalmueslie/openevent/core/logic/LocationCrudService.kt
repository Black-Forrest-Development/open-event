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
        val result = storage.create(request, event)
        notifyCreated(result)
        return result
    }

    fun findByEvent(event: Event): Location? {
        return storage.findByEvent(event)
    }

    fun updateByEvent(event: Event, request: LocationChangeRequest): Location {
        val existing = storage.findByEvent(event)
        return if (existing != null) {
            val result = storage.update(existing.id, request)
            notifyUpdated(result)
            result
        } else {
            val result = storage.create(request, event)
            notifyCreated(result)
            result
        }
    }

    fun deleteByEvent(event: Event): Location? {
        val existing = storage.findByEvent(event) ?: return null
        storage.delete(existing.id)
        notifyDeleted(existing)
        return existing
    }


}
