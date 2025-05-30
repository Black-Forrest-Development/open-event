package de.sambalmueslie.openevent.gateway.external.participant.api

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ExternalParticipantAddRequest(
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val mobile: String,
    val size: Long
)
