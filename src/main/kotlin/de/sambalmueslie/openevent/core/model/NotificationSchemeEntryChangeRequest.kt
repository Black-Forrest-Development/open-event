package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest

data class NotificationSchemeEntryChangeRequest(
    val key: String,
    val name: String,
    val description: String
) : BusinessObjectChangeRequest
