package de.sambalmueslie.openevent.core.logic.notification


import de.sambalmueslie.openevent.core.BaseCrudService
import de.sambalmueslie.openevent.core.logic.notification.api.NotificationSetting
import de.sambalmueslie.openevent.core.logic.notification.api.NotificationSettingChangeRequest
import de.sambalmueslie.openevent.core.logic.notification.db.NotificationSettingStorage
import de.sambalmueslie.openevent.core.model.PatchRequest
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class NotificationSettingCrudService(
    private val storage: NotificationSettingStorage,
) : BaseCrudService<Long, NotificationSetting, NotificationSettingChangeRequest, NotificationSettingChangeListener>(
    storage
) {


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(NotificationSettingCrudService::class.java)
    }

    fun findByName(name: String): NotificationSetting? {
        return storage.findByName(name)
    }

    fun setEnabled(id: Long, value: PatchRequest<Boolean>): NotificationSetting? {
        return storage.setEnabled(id, value)
    }
}
