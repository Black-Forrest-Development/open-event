package de.sambalmueslie.openevent.core.logic.notification.db

import de.sambalmueslie.openevent.core.Storage
import de.sambalmueslie.openevent.core.logic.notification.api.NotificationSetting
import de.sambalmueslie.openevent.core.logic.notification.api.NotificationSettingChangeRequest
import de.sambalmueslie.openevent.core.model.PatchRequest

interface NotificationSettingStorage : Storage<Long, NotificationSetting, NotificationSettingChangeRequest> {
    fun setEnabled(id: Long, value: PatchRequest<Boolean>): NotificationSetting?
    fun findByName(name: String): NotificationSetting?
}
