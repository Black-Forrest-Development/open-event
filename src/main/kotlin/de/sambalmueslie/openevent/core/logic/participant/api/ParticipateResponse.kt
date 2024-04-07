package de.sambalmueslie.openevent.core.logic.participant.api

import de.sambalmueslie.openevent.core.logic.registration.api.Registration

data class ParticipateResponse(
    val registration: Registration,
    val participant: Participant?,
    val participants: List<Participant>,
    val status: ParticipateStatus
)
