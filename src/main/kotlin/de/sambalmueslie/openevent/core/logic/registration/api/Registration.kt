package de.sambalmueslie.openevent.core.logic.registration.api

import de.sambalmueslie.openevent.core.BusinessObject
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Registration(
    override val id: Long,
    val eventId: Long,
    val maxGuestAmount: Int,
    val interestedAllowed: Boolean,
    val ticketsEnabled: Boolean
) : BusinessObject<Long>
