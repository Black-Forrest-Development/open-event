package de.sambalmueslie.openevent.gateway.external.event

import de.sambalmueslie.openevent.core.event.EventCrudService
import de.sambalmueslie.openevent.core.event.api.EventInfo
import de.sambalmueslie.openevent.core.share.ShareCrudService
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class ExternalEventService(
    private val service: EventCrudService,
    private val shareService: ShareCrudService
) {
    companion object {
        private val logger = LoggerFactory.getLogger(ExternalEventService::class.java)
    }

    fun getEvent(id: String): EventInfo? {
        val share = shareService.get(id) ?: return null
        return service.getInfo(share.eventId, null)
    }

    fun getPublicEvent(id: String): PublicEvent? {
        val share = shareService.get(id) ?: return null
        val event = service.getInfo(share.eventId, null) ?: return null
        return event.toPublicEvent(share)
    }
}