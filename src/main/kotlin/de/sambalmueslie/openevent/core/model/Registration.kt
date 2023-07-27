package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObject

data class Registration(
    override val id: Long,
    val maxGuestAmount: Int,
    val interestedAllowed: Boolean,
    val attendantAllowed: Boolean,
    val ticketsEnabled: Boolean
) : BusinessObject<Long>
