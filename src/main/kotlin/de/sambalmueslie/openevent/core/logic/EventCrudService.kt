package de.sambalmueslie.openevent.core.logic


import de.sambalmueslie.openevent.core.BaseCrudService
import de.sambalmueslie.openevent.core.model.Event
import de.sambalmueslie.openevent.core.model.EventChangeRequest
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class EventCrudService(
    private val storage: EventStorage,
    private val locationCrudService: LocationCrudService,
    private val registrationCrudService: RegistrationCrudService
) : BaseCrudService<Long, Event, EventChangeRequest>(storage, logger) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EventCrudService::class.java)
    }

    override fun create(request: EventChangeRequest, properties: Map<String, Any>): Event {
        val result = super.create(request, properties)
        request.location?.let { locationCrudService.create(result, it) }
        registrationCrudService.create(result, request.registration)
        return result
    }


}
