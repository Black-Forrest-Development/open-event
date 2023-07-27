package de.sambalmueslie.openevent.core.logic


import de.sambalmueslie.openevent.core.BaseCrudService
import de.sambalmueslie.openevent.core.model.Event
import de.sambalmueslie.openevent.core.model.Registration
import de.sambalmueslie.openevent.core.model.RegistrationChangeRequest
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class RegistrationCrudService(
    private val storage: RegistrationStorage
) : BaseCrudService<Long, Registration, RegistrationChangeRequest>(storage, logger) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(RegistrationCrudService::class.java)
    }

    fun create(event: Event, request: RegistrationChangeRequest): Registration {
        return storage.create(request, event)
    }
}
