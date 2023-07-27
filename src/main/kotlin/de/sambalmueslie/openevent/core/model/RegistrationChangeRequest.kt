package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest

data class RegistrationChangeRequest(
    val maxGuestAmount: Int,
    val interestedAllowed: Boolean,
    val attendantAllowed: Boolean,
    val ticketsEnabled: Boolean
) : BusinessObjectChangeRequest
