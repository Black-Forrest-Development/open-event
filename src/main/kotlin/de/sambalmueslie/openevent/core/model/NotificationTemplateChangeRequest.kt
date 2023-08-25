package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class NotificationTemplateChangeRequest(
    val subject: String,
    val lang: String,
    val content: String,
    val plain: Boolean,
) : BusinessObjectChangeRequest
