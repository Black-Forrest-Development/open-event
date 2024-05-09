package de.sambalmueslie.openevent.core.search.account

import com.jillesvangurp.searchdsls.querydsl.*
import de.sambalmueslie.openevent.core.account.api.Account
import de.sambalmueslie.openevent.core.search.api.AccountSearchRequest
import de.sambalmueslie.openevent.core.search.common.SearchQueryBuilder
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton

@Singleton
class AccountSearchQueryBuilder : SearchQueryBuilder<AccountSearchRequest> {
    override fun buildSearchQuery(
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