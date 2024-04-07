package de.sambalmueslie.openevent.core.logic.account.api

import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class PreferencesChangeRequest(
    val emailNotificationsPreferences: EmailNotificationsPreferences,
    val communicationPreferences: CommunicationPreferences,
    val notificationPreferences: NotificationPreferences
) : BusinessObjectChangeRequest



