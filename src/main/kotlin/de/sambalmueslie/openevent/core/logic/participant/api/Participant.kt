package de.sambalmueslie.openevent.core.logic.participant.api

import de.sambalmueslie.openevent.core.BusinessObject
import de.sambalmueslie.openevent.core.logic.account.api.Account
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime
@Serdeable
data class Participant(
    override val id: Long,
    val size: Long,
    val status: ParticipantStatus,
    val rank: Int,
    val waitingList: Boolean,
    val author: Account,
    val timestamp: LocalDateTime,
) : BusinessObject<Long>
