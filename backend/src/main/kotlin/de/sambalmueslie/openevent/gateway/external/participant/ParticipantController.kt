package de.sambalmueslie.openevent.gateway.external.participant

import de.sambalmueslie.openevent.core.participant.api.ParticipateRequest
import de.sambalmueslie.openevent.core.participant.api.ParticipateResponse
import de.sambalmueslie.openevent.gateway.external.participant.api.ExternalParticipantAddRequest
import de.sambalmueslie.openevent.gateway.external.participant.api.ExternalParticipantConfirmRequest
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/public/participant")
@Tag(name = "Public Participant API")
@Secured(SecurityRule.IS_ANONYMOUS)
class ParticipantController(
    private val service: ExternalParticipantService
) {
    @Post("/{eventId}/participant")
    fun requestParticipation(eventId: String, @Body request: ExternalParticipantAddRequest) {
        return service.requestParticipation(eventId, request)
    }


    @Put("/{eventId}/participant/{participantId}")
    fun changeParticipation(eventId: Long, participantId: String, @Body request: ParticipateRequest): ParticipateResponse? {
        return service.changeParticipation(eventId, participantId, request)
    }

    @Delete("/{eventId}/participant/{participantId}")
    fun cancelParticipation(eventId: Long, participantId: String): ParticipateResponse? {
        return service.cancelParticipation(eventId, participantId)
    }

    @Post("/{eventId}/participant/{participantId}")
    fun confirmParticipation(eventId: String, participantId: String, @Body request: ExternalParticipantConfirmRequest) {
        return service.confirmParticipation(eventId, participantId, request)
    }
}