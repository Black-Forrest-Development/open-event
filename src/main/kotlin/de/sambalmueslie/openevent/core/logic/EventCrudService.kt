package de.sambalmueslie.openevent.core.logic


import de.sambalmueslie.openevent.core.BaseCrudService
import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.core.model.Event
import de.sambalmueslie.openevent.core.model.EventChangeRequest
import de.sambalmueslie.openevent.core.model.PatchRequest
import de.sambalmueslie.openevent.error.InvalidRequestException
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class EventCrudService(
    private val accountCrudService: AccountCrudService,
    private val storage: EventStorage,
    private val locationCrudService: LocationCrudService,
    private val registrationCrudService: RegistrationCrudService
) : BaseCrudService<Long, Event, EventChangeRequest>(storage, logger) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EventCrudService::class.java)
    }

    fun create(request: EventChangeRequest, auth: Authentication): Event {
        val account = accountCrudService.get(auth) ?: throw InvalidRequestException("Cannot find account")
        return create(request, account)
    }

    fun create(request: EventChangeRequest, owner: Account): Event {
        val result = storage.create(request, owner)
        notifyCreated(result)
        request.location?.let { locationCrudService.create(result, it) }
        registrationCrudService.create(result, request.registration)
        return result
    }

    override fun update(id: Long, request: EventChangeRequest): Event {
        val result = super.update(id, request)
        if (request.location == null) {
            locationCrudService.deleteByEvent(result)
        } else {
            locationCrudService.updateByEvent(result, request.location)
        }
        registrationCrudService.updateByEvent(result, request.registration)
        return result
    }

    override fun delete(id: Long): Event? {
        val event = storage.get(id) ?: return null
        locationCrudService.deleteByEvent(event)
        registrationCrudService.deleteByEvent(event)
        return super.delete(id)
    }

    fun setPublished(id: Long, value: PatchRequest<Boolean>): Event? {
        return storage.setPublished(id, value)
    }


}
