package de.sambalmueslie.openevent.core.notification

import de.sambalmueslie.openevent.common.BusinessObjectChangeListener
import de.sambalmueslie.openevent.core.logic.notification.api.NotificationSetting

interface NotificationSettingChangeListener : BusinessObjectChangeListener<Long, NotificationSetting>
