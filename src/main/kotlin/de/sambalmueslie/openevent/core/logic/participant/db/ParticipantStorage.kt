package de.sambalmueslie.openevent.core.logic.participant.db

import de.sambalmueslie.openevent.core.Storage
import de.sambalmueslie.openevent.core.logic.account.api.Account
import de.sambalmueslie.openevent.core.logic.participant.api.Participant
import de.sambalmueslie.openevent.core.logic.participant.api.ParticipantChangeRequest
import de.sambalmueslie.openevent.core.logic.registration.api.Registration

interface ParticipantStorage : Storage<Long, Participant, ParticipantChangeRequest> {
    fun create(request: ParticipantChangeRequest, author: Account, registration: Registration): Participant
    fun get(registration: Registration): List<Participant>
    fun findByAccount(registration: Registration, account: Account): Participant?
    fun get(registration: List<Registration>): Map<Registration, List<Participant>>
}
