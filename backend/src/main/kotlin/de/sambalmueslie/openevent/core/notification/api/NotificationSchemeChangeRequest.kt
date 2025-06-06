package de.sambalmueslie.openevent.core.notification.api

import de.sambalmueslie.openevent.common.BusinessObjectChangeRequest
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class NotificationSchemeChangeRequest(
    val name: String,
    val enabled: Boolean,
) : BusinessObjectChangeRequest
