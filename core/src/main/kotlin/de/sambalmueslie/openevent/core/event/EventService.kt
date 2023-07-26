package de.sambalmueslie.openevent.core.event


import de.sambalmueslie.openevent.common.crud.GenericCrudService
import de.sambalmueslie.openevent.core.event.api.Event
import de.sambalmueslie.openevent.core.event.api.EventChangeRequest
import de.sambalmueslie.openevent.core.event.db.EventData
import de.sambalmueslie.openevent.core.event.db.EventRepository
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class EventService(private val repository: EventRepository) : GenericCrudService<Long, Event, EventChangeRequest, EventData>(repository, logger) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EventService::class.java)
    }

    override fun createData(request: EventChangeRequest): EventData {
        TODO("Not yet implemented")
    }

    override fun updateData(data: EventData, request: EventChangeRequest): EventData {
        TODO("Not yet implemented")
    }

    override fun isValid(request: EventChangeRequest) {
        TODO("Not yet implemented")
    }


}
