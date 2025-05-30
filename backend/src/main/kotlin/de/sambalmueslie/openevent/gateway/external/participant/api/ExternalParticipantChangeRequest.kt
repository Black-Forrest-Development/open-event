package de.sambalmueslie.openevent.gateway.external.participant.api

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ExternalParticipantChangeRequest(
    val size: Long
)
