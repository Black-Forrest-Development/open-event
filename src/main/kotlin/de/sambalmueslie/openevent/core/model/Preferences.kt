package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObject
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Preferences(
    override val id: Long,
    val emailNotificationsPreferences: EmailNotificationsPreferences,
    val communicationPreferences: CommunicationPreferences,
    val notificationPreferences: NotificationPreferences
) : BusinessObject<Long>

data class EmailNotificationsPreferences(
    val enabled: Boolean = true
    // TODO add some content
)

data class CommunicationPreferences(
    val enabled: Boolean = true
    // TODO add some content
)

data class NotificationPreferences(
    val enabled: Boolean = true
    // TODO add some content
)