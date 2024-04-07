package de.sambalmueslie.openevent.core.logic.notification.handler

import de.sambalmueslie.openevent.core.logic.event.api.Event
import de.sambalmueslie.openevent.core.logic.location.api.Location
import de.sambalmueslie.openevent.core.logic.participant.api.Participant
import de.sambalmueslie.openevent.core.logic.registration.api.Registration

data class RegistrationEventContent(
    val event: Event,
    val registration: Registration,
    val participant: Participant,
    val location: Location
)
