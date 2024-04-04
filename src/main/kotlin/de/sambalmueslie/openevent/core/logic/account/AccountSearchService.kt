package de.sambalmueslie.openevent.core.logic.account


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.jillesvangurp.searchdsls.mappingdsl.FieldMappings
import com.jillesvangurp.searchdsls.querydsl.ESQuery
import com.jillesvangurp.searchdsls.querydsl.SimpleQueryStringQuery
import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.infrastructure.search.BaseSearchService
import de.sambalmueslie.openevent.infrastructure.search.SearchService
import io.micronaut.context.annotation.Context
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Context
open class AccountSearchService(
    private val service: AccountCrudService,
    searchService: SearchService,
) : BaseSearchService<Long, Account>(service, searchService, "oe.account", logger), AccountChangeListener {


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AccountSearchService::class.java)
    }

    private val mapper = ObjectMapper().registerKotlinModule()

    init {
        service.register(this)
    }

    override fun createMappings(): FieldMappings.() -> Unit {
        return {
            number<Long>("id")
            text("name")
        }
    }

    override fun convert(obj: Account): String {
        val input = mutableMapOf<String, Any>()
        input["id"] = obj.id.toString()
        input["name"] = obj.name
        return mapper.writeValueAsString(input)
    }

    override fun buildQuery(q: String): ESQuery {
        return SimpleQueryStringQuery(q, "name")
    }


    override fun convertId(id: String): Long? {
        return id.toLongOrNull()
    }

    override fun handleCreated(actor: Account, obj: Account) {
        super.handleChanged(obj)
    }

    override fun handleUpdated(actor: Account, obj: Account) {
        super.handleChanged(obj)
    }

    override fun handleDeleted(actor: Account, obj: Account) {
        super.handleRemoved(obj)
    }

}
