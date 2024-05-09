package de.sambalmueslie.openevent.core.search.account

import com.jillesvangurp.ktsearch.ids
import com.jillesvangurp.ktsearch.search
import com.jillesvangurp.ktsearch.total
import com.jillesvangurp.searchdsls.querydsl.*
import de.sambalmueslie.openevent.core.account.AccountChangeListener
import de.sambalmueslie.openevent.core.account.AccountCrudService
import de.sambalmueslie.openevent.core.account.api.Account
import de.sambalmueslie.openevent.core.account.api.AccountInfo
import de.sambalmueslie.openevent.core.search.api.AccountSearchRequest
import de.sambalmueslie.openevent.core.search.api.AccountSearchResponse
import de.sambalmueslie.openevent.core.search.common.BaseOpenSearchOperator
import de.sambalmueslie.openevent.core.search.common.OpenSearchService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class AccountSearchOperator(
    private val service: AccountCrudService,

    private val fieldMapping: AccountFieldMappingProvider,

    openSearch: OpenSearchService
) : BaseOpenSearchOperator<Account, AccountSearchRequest, AccountSearchResponse>(openSearch, "oe.account", logger) {
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

    override fun initialLoadPage(pageable: Pageable): Page<Pair<String, String>> {
        val page = service.getInfos(pageable)
        return page.map { convert(it) }
    }

    private fun convert(obj: AccountInfo): Pair<String, String> {
        val input = AccountSearchEntryData.create(obj)
        return Pair(input.id, mapper.writeValueAsString(input))
    }

    override fun search(actor: Account, request: AccountSearchRequest, pageable: Pageable): AccountSearchResponse {
        val response = runBlocking {
            client.search(name, block = buildSearchQuery(pageable, request, actor))
        }

        val ids = response.ids.map { it.toLong() }.toSet()
        val result = service.getByIds(ids)

        return AccountSearchResponse(Page.of(result, pageable, response.total))
    }

    private fun buildSearchQuery(
        pageable: Pageable,
        request: AccountSearchRequest,
        actor: Account
    ): (SearchDSL.() -> Unit) = {
        from = pageable.offset.toInt()
        resultSize = pageable.size
        trackTotalHits = "true"
        query = bool {
            filter(
                multiMatch(
                    request.fullTextSearch, AccountSearchEntryData::name,
                    AccountSearchEntryData::email,
                    AccountSearchEntryData::firstName,
                    AccountSearchEntryData::lastName
                ) {
                    type = MultiMatchType.best_fields
                    lenient = true
                }
            )
        }
        sort {
            add("_score", SortOrder.DESC)
        }
    }
}