package de.sambalmueslie.openevent.gateway.external.participant

import de.sambalmueslie.openevent.common.findByIdOrNull
import de.sambalmueslie.openevent.config.AppConfig
import de.sambalmueslie.openevent.core.account.AccountCrudService
import de.sambalmueslie.openevent.core.account.api.Account
import de.sambalmueslie.openevent.core.account.api.AccountChangeRequest
import de.sambalmueslie.openevent.core.account.api.AccountSetupRequest
import de.sambalmueslie.openevent.core.account.api.ProfileChangeRequest
import de.sambalmueslie.openevent.core.event.api.EventInfo
import de.sambalmueslie.openevent.core.participant.ParticipantCrudService
import de.sambalmueslie.openevent.core.participant.api.ParticipantStatus
import de.sambalmueslie.openevent.core.participant.api.ParticipateRequest
import de.sambalmueslie.openevent.core.participant.api.ParticipateResponse
import de.sambalmueslie.openevent.core.registration.api.RegistrationInfo
import de.sambalmueslie.openevent.error.InvalidRequestException
import de.sambalmueslie.openevent.gateway.external.event.ExternalEventService
import de.sambalmueslie.openevent.gateway.external.participant.api.ExternalParticipantAddRequest
import de.sambalmueslie.openevent.gateway.external.participant.api.ExternalParticipantConfirmRequest
import de.sambalmueslie.openevent.gateway.external.participant.api.ExternalParticipantConfirmResponse
import de.sambalmueslie.openevent.gateway.external.participant.api.toExternalParticipant
import de.sambalmueslie.openevent.gateway.external.participant.db.ExternalParticipantData
import de.sambalmueslie.openevent.gateway.external.participant.db.ExternalParticipantRepository
import de.sambalmueslie.openevent.infrastructure.settings.SettingsService
import de.sambalmueslie.openevent.infrastructure.time.TimeProvider
import jakarta.inject.Singleton
import org.slf4j.LoggerFactory
import java.time.Duration
import java.time.format.DateTimeParseException
import java.util.*
import kotlin.random.Random

@Singleton
class ExternalParticipantService(
    private val eventService: ExternalEventService,
    private val accountService: AccountCrudService,
    private val settingsService: SettingsService,
    private val participantService: ParticipantCrudService,

    private val repository: ExternalParticipantRepository,
    private val timeProvider: TimeProvider,
    private val config: AppConfig
) {

    companion object {
        private val logger = LoggerFactory.getLogger(ExternalParticipantService::class.java)
        private val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
    }

    private val expires = try {
        Duration.parse(config.externalParticipantExpires)
    } catch (e: DateTimeParseException) {
        Duration.ofHours(24)
    }
    private val systemAccount = accountService.getSystemAccount()

    fun requestParticipation(eventId: String, request: ExternalParticipantAddRequest) {
        val event = eventService.getEvent(eventId) ?: return
        val registration = event.registration ?: return

        validate(request)
        val existing = repository.findByEventIdAndEmail(event.event.id, request.email)
        if (existing != null) {
            requestParticipationForExisting(event, existing, request)
        } else {
            requestParticipationNew(event, registration, request)
        }
    }

    private fun requestParticipationNew(event: EventInfo, registration: RegistrationInfo, request: ExternalParticipantAddRequest): ExternalParticipantData? {
        val id = UUID.randomUUID().toString()
        val now = timeProvider.now()
        val expiresTimestamp = now.plus(expires)
        val code = Random.nextInt(100000).toString().format("%05d")
        return repository.save(ExternalParticipantData.create(id, event, request, code, expiresTimestamp, now))
    }

    private fun requestParticipationForExisting(event: EventInfo, existing: ExternalParticipantData, request: ExternalParticipantAddRequest) {
        val now = timeProvider.now()
        val expiresTimestamp = now.plus(expires)
        repository.update(existing.updateExpires(expiresTimestamp, now))
    }


    private fun validate(request: ExternalParticipantAddRequest) {
        if (request.email.isBlank()) throw InvalidRequestException("E-Mail cannot be blank")
        if (request.firstName.isBlank()) throw InvalidRequestException("First name cannot be blank")
        if (request.lastName.isBlank()) throw InvalidRequestException("Last name cannot be blank")
        if (request.size <= 0) throw InvalidRequestException("Size must be a positive number")
        val validEmail = emailRegex.matches(request.email)
        if (!validEmail) throw InvalidRequestException("Provided E-Mail is invalid")
    }

    fun changeParticipation(eventId: Long, participantId: String, request: ParticipateRequest): ParticipateResponse? {
        TODO("Not yet implemented")
    }

    fun cancelParticipation(eventId: Long, participantId: String): ParticipateResponse? {
        TODO("Not yet implemented")
    }

    fun confirmParticipation(eventId: String, participantId: String, request: ExternalParticipantConfirmRequest): ExternalParticipantConfirmResponse {
        val event = eventService.getEvent(eventId) ?: return ExternalParticipantConfirmResponse.failed()
        val registration = event.registration ?: return ExternalParticipantConfirmResponse.failed()
        val participant = repository.findByIdOrNull(participantId) ?: return ExternalParticipantConfirmResponse.failed()
        val eventValid = participant.eventId == event.event.id
        if (!eventValid) return ExternalParticipantConfirmResponse.failed()

        val codeValid = request.code == participant.code
        return if (!codeValid) {
            handleInvalidConfirmation(participant)
        } else {
            handleValidConfirmation(event, registration, participant)
        }
    }

    private fun handleInvalidConfirmation(participant: ExternalParticipantData): ExternalParticipantConfirmResponse {
        if (participant.invalidConfirmationTrials >= config.maxConfirmationTrials) {
            repository.delete(participant)
        } else {
            participant.invalidConfirmationTrials++
            repository.update(participant)
        }
        return ExternalParticipantConfirmResponse.failed()
    }

    private fun handleValidConfirmation(event: EventInfo, registration: RegistrationInfo, participant: ExternalParticipantData): ExternalParticipantConfirmResponse {
        val (_, account) = getOrCreateAccount(participant) ?: return ExternalParticipantConfirmResponse.failed()
        if (registration.participants.any { it.author.id == account.id }) return ExternalParticipantConfirmResponse.failed()

        val participateRequest = ParticipateRequest(participant.size)
        val participateResponse = participantService.change(systemAccount, registration.registration, account, participateRequest, ParticipantStatus.ACCEPTED)
        repository.delete(participant)
        return ExternalParticipantConfirmResponse(participateResponse.participant?.toExternalParticipant(), participateResponse.status)
    }

    private fun getOrCreateAccount(data: ExternalParticipantData): Pair<Boolean, Account>? {
        val account = accountService.findByEmail(data.email)
        if (account != null) return Pair(false, account)


        val name = "${data.firstName} ${data.lastName}"
        val defaultLanguage = settingsService.getLanguage()
        val accountRequest = AccountSetupRequest(
            AccountChangeRequest(name, "", null),
            ProfileChangeRequest(data.email, data.phone, data.mobile, data.firstName, data.lastName, language = defaultLanguage),
        )
        val info = accountService.setup(systemAccount, accountRequest)
        val createdAccount = accountService.get(info.id) ?: return null
        return Pair(true, createdAccount)
    }

}