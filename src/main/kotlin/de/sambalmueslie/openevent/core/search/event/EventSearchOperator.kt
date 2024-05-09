package de.sambalmueslie.openevent.core.search.event

import com.jillesvangurp.ktsearch.*
import com.jillesvangurp.ktsearch.SearchResponse
import de.sambalmueslie.openevent.core.account.api.Account
import de.sambalmueslie.openevent.core.account.db.AccountStorageService
import de.sambalmueslie.openevent.core.event.EventCrudService
import de.sambalmueslie.openevent.core.event.api.EventInfo
import de.sambalmueslie.openevent.core.search.api.*
import de.sambalmueslie.openevent.core.search.common.BaseOpenSearchOperator
import de.sambalmueslie.openevent.core.search.common.OpenSearchService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Singleton
open class EventSearchOperator(
    private val service: EventCrudService,
    private val accountService: AccountStorageService,

    private val fieldMapping: EventFieldMappingProvider,
    private val queryBuilder: EventSearchQueryBuilder,

    openSearch: OpenSearchService
) : BaseOpenSearchOperator<EventSearchEntry, EventSearchRequest, EventSearchResponse>(openSearch, "oe.event", logger) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EventSearchOperator::class.java)
    }

    init {
        service.registerSearch { evt -> handleChanged(evt) }
    }

    override fun getFieldMappingProvider() = fieldMapping
    override fun getSearchQueryBuilder() = queryBuilder

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

    override fun processSearchResponse(
        actor: Account,
        request: SearchRequest,
        response: SearchResponse,
        pageable: Pageable
    ): EventSearchResponse {
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


}