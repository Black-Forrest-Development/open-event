package de.sambalmueslie.openevent.core.logic.notification

import de.sambalmueslie.openevent.core.BusinessObjectChangeListener
import de.sambalmueslie.openevent.core.logic.notification.api.NotificationScheme

interface NotificationSchemeChangeListener : BusinessObjectChangeListener<Long, NotificationScheme>
