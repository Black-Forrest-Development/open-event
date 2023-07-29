package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest

data class MessageChangeRequest(
    val subject: String,
    val content: String,
) : BusinessObjectChangeRequest
