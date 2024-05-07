package de.sambalmueslie.openevent.core.search.operator

import com.jillesvangurp.ktsearch.*
import com.jillesvangurp.searchdsls.querydsl.*
import de.sambalmueslie.openevent.core.account.api.Account
import de.sambalmueslie.openevent.core.account.db.AccountStorageService
import de.sambalmueslie.openevent.core.event.EventCrudService
import de.sambalmueslie.openevent.core.event.api.EventInfo
import de.sambalmueslie.openevent.core.search.api.DateHistogramEntry
import de.sambalmueslie.openevent.core.search.api.EventSearchEntry
import de.sambalmueslie.openevent.core.search.api.EventSearchRequest
import de.sambalmueslie.openevent.core.search.api.EventSearchResponse
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Singleton
open class EventSearchOperator(
    private val service: EventCrudService,
    private val accountService: AccountStorageService,
    openSearch: OpenSearchService
) : BaseOpenSearchOperator<EventSearchEntry, EventSearchRequest, EventSearchResponse>(openSearch, "oe.event", logger) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EventSearchOperator::class.java)
    }

    init {
        service.registerSearch { evt -> handleChanged(evt) }
    }

    override fun createMappings() = EventSearchEntryData.createMappings()

    override fun initialLoadPage(pageable: Pageable): Page<Pair<String, String>> {
        val page = service.getInfos(pageable)
        return page.map { convert(it) }
    }

    private fun convert(obj: EventInfo): Pair<String, String> {
        val input = EventSearchEntryData.create(obj)
        return Pair(input.id, mapper.writeValueAsString(input))
    }

    private fun handleChanged(obj: EventInfo) {
        val data = convert(obj)
        updateDocument(data)
    }

    override fun search(actor: Account, request: EventSearchRequest, pageable: Pageable): EventSearchResponse {
        val response = runBlocking {
            client.search(name, block = buildSearchQuery(pageable, request, actor))
        }

        val data = response.hits?.hits?.map {
            it.parseHit<EventSearchEntryData>()
        } ?: emptyList()

        val dateHistogram = response.aggregations.dateHistogramResult(EventSearchEntryData::date.name).buckets
            .map { it.parse<DateHistogramBucket>() }
            .map { DateHistogramEntry(LocalDate.parse(it.keyAsString, DateTimeFormatter.ISO_DATE_TIME), it.docCount) }

        val content = data.mapNotNull {
            val owner = accountService.getInfo(it.owner) ?: return@mapNotNull null
            it.convert(owner)
        }

        return EventSearchResponse(Page.of(content, pageable, response.total), dateHistogram)
    }

    private fun buildSearchQuery(
        pageable: Pageable,
        request: EventSearchRequest,
        actor: Account
    ): SearchDSL.() -> Unit = {
        from = pageable.offset.toInt()
        resultSize = pageable.size
        trackTotalHits = "true"
        query = bool {
            if (request.fullTextSearch.isNotBlank()) {
                put("minimum_should_match", 1)
                should(
                    match(EventSearchEntryData::title, request.fullTextSearch) { boost = 2.0 },
                    matchBoolPrefix(EventSearchEntryData::title, request.fullTextSearch),
                    match(EventSearchEntryData::shortText, request.fullTextSearch),
                    match(EventSearchEntryData::longText, request.fullTextSearch)
                )
            }
            if (request.from != null) {
                filter(range(EventSearchEntryData::date) {
                    gte = request.from.atStartOfDay()
                })
            }
            if (request.to != null) {
                filter(range(EventSearchEntryData::date) {
                    lte = request.to.atStartOfDay()
                })
            }
            if (request.ownEvents) {
                filter(term(EventSearchEntryData::owner, actor.id.toString()))
            }
            if (request.participatingEvents) {
                filter(matchPhrase(EventSearchEntryData::participant, actor.id.toString()))
            }
            if (request.onlyAvailableEvents) {
                filter(term(EventSearchEntryData::hasSpaceLeft, "true"))
            }
            agg(
                EventSearchEntryData::date.name,
                DateHistogramAgg(EventSearchEntryData::date) {
                    calendarInterval = "day"
                }
            )

        }
        sort {
            add(EventSearchEntryData::date, SortOrder.ASC)
        }
    }


}