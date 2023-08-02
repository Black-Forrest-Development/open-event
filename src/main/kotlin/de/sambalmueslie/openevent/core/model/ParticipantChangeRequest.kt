package de.sambalmueslie.openevent.core.model


import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest
import io.micronaut.serde.annotation.Serdeable

@Serdeable
class ParticipantChangeRequest(
    val size: Long,
    val status: ParticipantStatus,
) : BusinessObjectChangeRequest
