package de.sambalmueslie.openevent.core.logic.event


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.jillesvangurp.searchdsls.mappingdsl.FieldMappings
import com.jillesvangurp.searchdsls.querydsl.ESQuery
import com.jillesvangurp.searchdsls.querydsl.SimpleQueryStringQuery
import de.sambalmueslie.openevent.core.logic.account.api.Account
import de.sambalmueslie.openevent.core.logic.event.api.Event
import de.sambalmueslie.openevent.core.logic.event.api.EventInfo
import de.sambalmueslie.openevent.infrastructure.search.BaseSearchService
import de.sambalmueslie.openevent.infrastructure.search.SearchService
import io.micronaut.context.annotation.Context
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import org.jsoup.Jsoup
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Context
open class EventSearchService(
    private val service: EventCrudService,
    searchService: SearchService,
) : BaseSearchService<Long, Event>(service, searchService, "oe.event", logger), EventChangeListener {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EventSearchService::class.java)
    }

    private val mapper = ObjectMapper().registerKotlinModule()

    init {
        service.register(this)
    }


    override fun createMappings(): FieldMappings.() -> Unit {
        return {
            number<Long>("id")
            text("title")
            text("content")
            number<Long>("owner")
            bool("published")
        }
    }


    override fun convert(obj: Event): String {
        val input = mutableMapOf<String, Any>()
        input["id"] = obj.id.toString()
        input["title"] = obj.title
        input["content"] = Jsoup.parse(obj.longText).text()
        input["owner"] = obj.owner.id
        input["published"] = obj.published
        return mapper.writeValueAsString(input)
    }

    override fun buildQuery(q: String): ESQuery {
        return SimpleQueryStringQuery(q,  "title", "content")
    }


    override fun convertId(id: String): Long? {
        return id.toLongOrNull()
    }

    fun searchInfo(query: String, pageable: Pageable): Page<EventInfo> {
        val result = search(query, pageable)
        return service.convertInfo(result)
    }

    override fun handleCreated(actor: Account, obj: Event) {
        super<BaseSearchService>.handleCreated(obj)
    }

    override fun handleUpdated(actor: Account, obj: Event) {
        super.handleChanged(obj)
    }

    override fun publishedChanged(actor: Account, event: Event) {
        super.handleChanged(event)
    }

    override fun handleDeleted(actor: Account, obj: Event) {
        super.handleRemoved(obj)
    }

}
