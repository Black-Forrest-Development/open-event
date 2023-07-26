package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest

data class AccountChangeRequest(
    val name: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val iconUrl: String,
) : BusinessObjectChangeRequest
