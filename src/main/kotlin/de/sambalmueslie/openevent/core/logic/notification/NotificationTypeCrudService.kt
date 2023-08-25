package de.sambalmueslie.openevent.core.logic.notification


import de.sambalmueslie.openevent.core.BaseCrudService
import de.sambalmueslie.openevent.core.model.NotificationType
import de.sambalmueslie.openevent.core.model.NotificationTypeChangeRequest
import de.sambalmueslie.openevent.core.storage.NotificationTypeStorage
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class NotificationTypeCrudService(
    private val storage: NotificationTypeStorage
) : BaseCrudService<Long, NotificationType, NotificationTypeChangeRequest, NotificationTypeChangeListener>(storage) {


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(NotificationTypeCrudService::class.java)
    }

}
