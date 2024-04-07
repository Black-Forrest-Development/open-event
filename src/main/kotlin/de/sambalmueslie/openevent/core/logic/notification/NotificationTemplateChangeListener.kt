package de.sambalmueslie.openevent.core.logic.notification

import de.sambalmueslie.openevent.core.BusinessObjectChangeListener
import de.sambalmueslie.openevent.core.logic.notification.api.NotificationTemplate

interface NotificationTemplateChangeListener : BusinessObjectChangeListener<Long, NotificationTemplate>
