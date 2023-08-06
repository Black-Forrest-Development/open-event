package de.sambalmueslie.openevent.api

import de.sambalmueslie.openevent.core.model.NotificationScheme
import de.sambalmueslie.openevent.core.model.NotificationSchemeChangeRequest

interface NotificationAPI : CrudAPI<Long, NotificationScheme, NotificationSchemeChangeRequest> {
    companion object {
        const val PERMISSION_READ = "openevent.notification.read"
        const val PERMISSION_WRITE = "openevent.notification.write"
        const val PERMISSION_ADMIN = "openevent.notification.admin"
    }
}
