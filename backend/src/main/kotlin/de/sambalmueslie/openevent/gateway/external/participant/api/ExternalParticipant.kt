package de.sambalmueslie.openevent.gateway.external.participant.api

import de.sambalmueslie.openevent.core.participant.api.Participant
import de.sambalmueslie.openevent.core.participant.api.ParticipantStatus
import de.sambalmueslie.openevent.gateway.external.account.PublicAccount
import de.sambalmueslie.openevent.gateway.external.account.toPublicAccount
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime

@Serdeable
data class ExternalParticipant(
    val size: Long,
    val status: ParticipantStatus,
    val rank: Int,
    val waitingList: Boolean,
    val author: PublicAccount,
    val timestamp: LocalDateTime,
)


fun Participant.toExternalParticipant(): ExternalParticipant {
    return ExternalParticipant(size, status, rank, waitingList, author.toPublicAccount(), timestamp)
}
