package de.sambalmueslie.openevent.core.search.account

import com.jillesvangurp.ktsearch.SearchResponse
import com.jillesvangurp.ktsearch.parseHit
import com.jillesvangurp.ktsearch.total
import de.sambalmueslie.openevent.core.account.AccountChangeListener
import de.sambalmueslie.openevent.core.account.AccountCrudService
import de.sambalmueslie.openevent.core.account.api.Account
import de.sambalmueslie.openevent.core.account.api.AccountInfo
import de.sambalmueslie.openevent.core.search.api.AccountSearchEntry
import de.sambalmueslie.openevent.core.search.api.AccountSearchRequest
import de.sambalmueslie.openevent.core.search.api.AccountSearchResponse
import de.sambalmueslie.openevent.core.search.api.SearchRequest
import de.sambalmueslie.openevent.core.search.common.BaseOpenSearchOperator
import de.sambalmueslie.openevent.core.search.common.OpenSearchService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class AccountSearchOperator(
    private val service: AccountCrudService,

    private val fieldMapping: AccountFieldMappingProvider,
    private val queryBuilder: AccountSearchQueryBuilder,

    openSearch: OpenSearchService
) : BaseOpenSearchOperator<AccountSearchEntry, AccountSearchRequest, AccountSearchResponse>(
    openSearch,
    "oe.account",
    logger
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AccountSearchOperator::class.java)
    }

    init {
        service.register(object : AccountChangeListener {
            override fun handleCreated(actor: Account, obj: Account) {
                handleChanged(obj)
            }

            override fun handleUpdated(actor: Account, obj: Account) {
                handleChanged(obj)
            }

            override fun handleDeleted(actor: Account, obj: Account) {
                deleteDocument(obj.id.toString())
            }
        })
    }

    private fun handleChanged(account: Account) {
        val info = service.getInfo(account)
        val data = convert(info)
        updateDocument(data)
    }

    override fun getFieldMappingProvider() = fieldMapping
    override fun getSearchQueryBuilder() = queryBuilder

    override fun initialLoadPage(pageable: Pageable): Page<Pair<String, String>> {
        val page = service.getInfos(pageable)
        return page.map { convert(it) }
    }

    private fun convert(obj: AccountInfo): Pair<String, String> {
        val input = AccountSearchEntryData.create(obj)
        return Pair(input.id, mapper.writeValueAsString(input))
    }

    override fun processSearchResponse(
        actor: Account,
        request: SearchRequest,
        response: SearchResponse,
        pageable: Pageable
    ): AccountSearchResponse {
        val data = response.hits?.hits?.map { it.parseHit<AccountSearchEntryData>() }
            ?.map { it.convert() } ?: emptyList()

        return AccountSearchResponse(Page.of(data, pageable, response.total))
    }

}