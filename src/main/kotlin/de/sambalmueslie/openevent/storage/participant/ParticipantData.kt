package de.sambalmueslie.openevent.storage.participant

import de.sambalmueslie.openevent.core.model.*
import de.sambalmueslie.openevent.storage.DataObject
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "Participant")
@Table(name = "participant")
data class ParticipantData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,

    @Column var registrationId: Long,
    @Column var authorId: Long,

    @Column var size: Long,
    @Column var status: ParticipantStatus,
    @Column var rank: Int,
    @Column var waitingList: Boolean,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) : DataObject {
    companion object {
        fun create(
            registration: Registration,
            account: Account,
            request: ParticipantChangeRequest,
            timestamp: LocalDateTime
        ): ParticipantData {
            return ParticipantData(0, registration.id, account.id, request.size, request.status, -1, false, timestamp)
        }
    }

    fun convert(account: Account): Participant {
        return Participant(id, size, status, rank, waitingList, account, updated ?: created)
    }

    fun update(request: ParticipantChangeRequest, timestamp: LocalDateTime): ParticipantData {
        size = request.size
        status = request.status
        updated = timestamp
        return this
    }
}
