package de.sambalmueslie.openevent.core.logic.registration

import de.sambalmueslie.openevent.core.BusinessObjectChangeListener
import de.sambalmueslie.openevent.core.logic.account.api.Account
import de.sambalmueslie.openevent.core.logic.participant.api.Participant
import de.sambalmueslie.openevent.core.logic.participant.api.ParticipateStatus
import de.sambalmueslie.openevent.core.logic.registration.api.Registration

interface RegistrationChangeListener : BusinessObjectChangeListener<Long, Registration> {
    fun participantChanged(
        actor: Account,
        registration: Registration,
        participant: Participant,
        status: ParticipateStatus
    )

    fun participantRemoved(actor: Account, registration: Registration, participant: Participant)
}
