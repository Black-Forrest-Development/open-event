package de.sambalmueslie.openevent.core.logic.history


import de.sambalmueslie.openevent.core.logic.account.AccountCrudService
import de.sambalmueslie.openevent.core.logic.event.EventCrudService
import de.sambalmueslie.openevent.core.logic.event.api.EventInfo
import de.sambalmueslie.openevent.core.logic.history.api.HistoryEntryChangeRequest
import de.sambalmueslie.openevent.core.logic.history.api.HistoryEntrySource
import de.sambalmueslie.openevent.core.logic.history.api.HistoryEntryType
import de.sambalmueslie.openevent.core.logic.history.db.HistoryEntryStorage
import de.sambalmueslie.openevent.core.logic.history.handler.EventNotificationHandler
import de.sambalmueslie.openevent.core.logic.history.handler.RegistrationNotificationHandler
import de.sambalmueslie.openevent.core.logic.participant.api.Participant
import de.sambalmueslie.openevent.core.logic.participant.api.ParticipateStatus
import de.sambalmueslie.openevent.storage.util.PageableSequence
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class HistoryMigrationService(
    private val accountService:AccountCrudService,
    private val eventService: EventCrudService,
    private val storage: HistoryEntryStorage
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(HistoryMigrationService::class.java)
    }

    fun migrate() {
        storage.deleteAll()
        val events = PageableSequence { eventService.getInfos(it) }
        events.forEach { migrateEvent(it) }
    }

    private fun migrateEvent(event: EventInfo) {
        addEventCreatedEntry(event)

        if (event.event.changed != null) {
            addEventChangedEntry(event)
        }


        val participants = event.registration?.participants ?: return

        participants.forEach {
            addParticipantChangedEntry(it, event)
        }
    }

    private fun addEventCreatedEntry(event: EventInfo) {
        val createRequest = HistoryEntryChangeRequest(
            HistoryEntryType.EVENT_CREATED, EventNotificationHandler.KEY_EVENT_CREATED, HistoryEntrySource.EVENT, ""
        )
        val owner = accountService.get(event.event.owner.id) ?: return
        storage.create(createRequest, event.event, owner, event.event.created)
    }
    private fun addEventChangedEntry(event: EventInfo) {
        val changeRequest = HistoryEntryChangeRequest(
            HistoryEntryType.EVENT_CHANGED, EventNotificationHandler.KEY_EVENT_UPDATED, HistoryEntrySource.EVENT, ""
        )
        val owner = accountService.get(event.event.owner.id) ?: return
        storage.create(changeRequest, event.event, owner, event.event.changed!!)
    }

    private fun addParticipantChangedEntry(
        it: Participant,
        event: EventInfo
    ) {
        val status = if (it.waitingList) ParticipateStatus.WAITING_LIST else ParticipateStatus.ACCEPTED
        val participantRequest = HistoryEntryChangeRequest(
            HistoryEntryType.PARTICIPANT_STATUS_CHANGED,
            RegistrationNotificationHandler.KEY_PARTICIPANT_UPDATED,
            HistoryEntrySource.REGISTRATION,
            status.toString()
        )
        val author = accountService.get(it.author.id) ?: return
        storage.create(participantRequest, event.event, author, it.timestamp)
    }



}
