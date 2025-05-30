package de.sambalmueslie.openevent.core.notification.handler

import de.sambalmueslie.openevent.core.event.api.Event
import de.sambalmueslie.openevent.core.registration.api.Registration
import de.sambalmueslie.openevent.gateway.external.participant.db.ExternalParticipantData

data class ExternalParticipantEventContent(
    val event: Event,
    val registration: Registration,
    val participant: ExternalParticipantData
)
