package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest

data class CategoryChangeRequest(
    val name: String,
    val iconUrl: String
) : BusinessObjectChangeRequest
