package de.sambalmueslie.openevent.core.search.operator

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.jillesvangurp.ktsearch.SearchResponse
import com.jillesvangurp.ktsearch.parseHit
import com.jillesvangurp.ktsearch.total
import de.sambalmueslie.openevent.core.account.api.Account
import de.sambalmueslie.openevent.core.account.db.AccountStorageService
import de.sambalmueslie.openevent.core.event.EventChangeListener
import de.sambalmueslie.openevent.core.event.EventCrudService
import de.sambalmueslie.openevent.core.event.api.Event
import de.sambalmueslie.openevent.core.event.api.EventInfo
import de.sambalmueslie.openevent.core.location.LocationChangeListener
import de.sambalmueslie.openevent.core.location.LocationCrudService
import de.sambalmueslie.openevent.core.location.api.Location
import de.sambalmueslie.openevent.core.participant.api.Participant
import de.sambalmueslie.openevent.core.participant.api.ParticipateStatus
import de.sambalmueslie.openevent.core.registration.RegistrationChangeListener
import de.sambalmueslie.openevent.core.registration.RegistrationCrudService
import de.sambalmueslie.openevent.core.registration.api.Registration
import de.sambalmueslie.openevent.core.search.api.EventSearchEntry
import de.sambalmueslie.openevent.core.search.api.EventSearchRequest
import de.sambalmueslie.openevent.core.search.api.EventSearchResponse
import de.sambalmueslie.openevent.core.search.api.SearchRequest
import de.sambalmueslie.openevent.infrastructure.search.OpenSearchService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class EventSearchOperator(
    private val service: EventCrudService,
    private val registrationService: RegistrationCrudService,
    private val locationService: LocationCrudService,
    private val accountService: AccountStorageService,
    openSearch: OpenSearchService
) : BaseOpenSearchOperator<EventSearchEntry, EventSearchRequest, EventSearchResponse>(openSearch, "oe.event", logger) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EventSearchOperator::class.java)
    }


    init {
        service.register(object : EventChangeListener {
            override fun handleCreated(actor: Account, obj: Event) {
                handleChanged(service.getInfo(obj, actor))
            }

            override fun handleUpdated(actor: Account, obj: Event) {
                handleChanged(service.getInfo(obj, actor))
            }

            override fun publishedChanged(actor: Account, event: Event) {
                handleChanged(service.getInfo(event, actor))
            }

            override fun handleDeleted(actor: Account, obj: Event) {
                deleteDocument(obj.id.toString())
            }
        })

        registrationService.register(object : RegistrationChangeListener {
            override fun participantChanged(
                actor: Account, registration: Registration, participant: Participant, status: ParticipateStatus
            ) {
                handleChanged(registration.eventId, actor)
            }

            override fun participantRemoved(actor: Account, registration: Registration, participant: Participant) {
                handleChanged(registration.eventId, actor)
            }
        })

        locationService.register(object : LocationChangeListener {
            override fun handleUpdated(actor: Account, obj: Location) {
                handleChanged(obj.eventId, actor)
            }
        })
    }

    private val mapper = ObjectMapper()
        .registerKotlinModule()
        .registerModule(JavaTimeModule())
        .setSerializationInclusion(JsonInclude.Include.ALWAYS)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)

    override fun createMappings() = EventSearchEntryData.createMappings()

    override fun initialLoadPage(pageable: Pageable): Page<Pair<String, String>> {
        val page = service.getInfos(pageable)
        return page.map { convert(it) }
    }

    private fun convert(obj: EventInfo): Pair<String, String> {
        val input = EventSearchEntryData.create(obj)
        return Pair(input.id, mapper.writeValueAsString(input))
    }

    private fun handleChanged(eventId: Long, actor: Account) {
        val event = service.getInfo(eventId, actor) ?: return
        handleChanged(event)
    }

    private fun handleChanged(obj: EventInfo) {
        val data = convert(obj)
        updateDocument(data)
    }


    override fun buildQuery(actor: Account, request: EventSearchRequest) =
        EventSearchEntryData.buildQuery(actor, request)

    override fun toResult(
        actor: Account,
        request: SearchRequest,
        pageable: Pageable,
        result: SearchResponse
    ): EventSearchResponse {
        val data = result.hits?.hits?.map {
            it.parseHit<EventSearchEntryData>()
        } ?: emptyList()

        val content = data.mapNotNull {
            val owner = accountService.getInfo(it.owner) ?: return@mapNotNull null
            it.convert(owner)
        }

        return EventSearchResponse(Page.of(content, pageable, result.total))
    }

}