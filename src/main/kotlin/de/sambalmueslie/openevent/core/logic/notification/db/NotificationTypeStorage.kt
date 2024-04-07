package de.sambalmueslie.openevent.core.logic.notification.db

import de.sambalmueslie.openevent.core.Storage
import de.sambalmueslie.openevent.core.logic.notification.api.NotificationType
import de.sambalmueslie.openevent.core.logic.notification.api.NotificationTypeChangeRequest

interface NotificationTypeStorage : Storage<Long, NotificationType, NotificationTypeChangeRequest> {
    fun findByKey(key: String): NotificationType?
    fun findByKeys(keys: Set<String>): List<NotificationType>
}
