package de.sambalmueslie.openevent.storage.event


import de.sambalmueslie.openevent.core.logic.EventStorage
import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.core.model.Event
import de.sambalmueslie.openevent.core.model.EventChangeRequest
import de.sambalmueslie.openevent.error.InvalidRequestException
import de.sambalmueslie.openevent.infrastructure.cache.CacheService
import de.sambalmueslie.openevent.infrastructure.time.TimeProvider
import de.sambalmueslie.openevent.storage.BaseStorageService
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class EventStorageService(
    private val repository: EventRepository,
    private val converter: EventConverter,
    cacheService: CacheService,
    private val timeProvider: TimeProvider,
) : BaseStorageService<Long, Event, EventChangeRequest, EventData>(
    repository,
    converter,
    cacheService,
    Event::class,
    logger
), EventStorage {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EventStorageService::class.java)
        private const val OWNER_REFERENCE = "owner"
    }

    override fun create(request: EventChangeRequest, owner: Account): Event {
        return create(request, mapOf(Pair(OWNER_REFERENCE, owner)))
    }

    override fun createData(request: EventChangeRequest, properties: Map<String, Any>): EventData {
        val owner = properties[OWNER_REFERENCE] as? Account ?: throw InvalidRequestException("Cannot find account")
        return EventData.create(owner, request, timeProvider.now())
    }

    override fun updateData(data: EventData, request: EventChangeRequest): EventData {
        return data.update(request, timeProvider.now())
    }

    override fun isValid(request: EventChangeRequest) {
        if (request.title.isBlank()) throw InvalidRequestException("Title cannot be blank")
    }


}
