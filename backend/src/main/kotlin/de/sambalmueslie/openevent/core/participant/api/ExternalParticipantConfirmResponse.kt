package de.sambalmueslie.openevent.gateway.external.participant.api

import de.sambalmueslie.openevent.core.participant.api.ParticipateStatus
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ExternalParticipantConfirmResponse(
    val participant: ExternalParticipant?,
    val status: ParticipateStatus
) {
    companion object {
        fun failed() = ExternalParticipantConfirmResponse(null, ParticipateStatus.FAILED)
    }
}
