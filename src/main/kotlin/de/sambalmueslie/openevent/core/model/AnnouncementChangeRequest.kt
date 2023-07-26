package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest

data class AnnouncementChangeRequest(
    val subject: String,
    val content: String
) : BusinessObjectChangeRequest
