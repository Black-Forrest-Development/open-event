package de.sambalmueslie.openevent.core.notification.handler

import de.sambalmueslie.openevent.core.event.api.Event
import de.sambalmueslie.openevent.core.participant.db.ExternalParticipantData
import de.sambalmueslie.openevent.core.registration.api.Registration

data class ExternalParticipantEventContent(
    val url: String,
    val event: Event,
    val registration: Registration,
    val participant: ExternalParticipantData
)
