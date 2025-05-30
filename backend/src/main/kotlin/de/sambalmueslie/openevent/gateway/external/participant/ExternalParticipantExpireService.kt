package de.sambalmueslie.openevent.gateway.external.participant

import de.sambalmueslie.openevent.common.PageableSequence
import de.sambalmueslie.openevent.core.account.AccountCrudService
import de.sambalmueslie.openevent.core.event.EventCrudService
import de.sambalmueslie.openevent.core.participant.ParticipantCrudService
import de.sambalmueslie.openevent.core.registration.RegistrationCrudService
import de.sambalmueslie.openevent.gateway.external.participant.db.ExternalParticipantData
import de.sambalmueslie.openevent.gateway.external.participant.db.ExternalParticipantRepository
import de.sambalmueslie.openevent.infrastructure.time.TimeProvider
import io.micronaut.scheduling.annotation.Scheduled
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory

@Singleton
class ExternalParticipantExpireService(
    private val repository: ExternalParticipantRepository,
    private val eventService: EventCrudService,
    private val registrationService: RegistrationCrudService,
    private val accountService: AccountCrudService,
    private val participantService: ParticipantCrudService,
    private val timeProvider: TimeProvider
) {

    companion object {
        private val logger = LoggerFactory.getLogger(ExternalParticipantExpireService::class.java)
    }

    private val systemAccount = accountService.getSystemAccount()

    @Scheduled(cron = "0 0 4 * * ?")
    fun detectExpiredExternalParticipants() {
        val timestamp = timeProvider.now()
        val sequence = PageableSequence { repository.findByExpiresLessThanEquals(timestamp, it) }
        sequence.forEach { handleExpiredExternalParticipant(it) }
    }

    private fun handleExpiredExternalParticipant(participant: ExternalParticipantData) {
        repository.delete(participant)
    }
}