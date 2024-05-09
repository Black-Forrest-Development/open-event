package de.sambalmueslie.openevent.core.search.category

import com.jillesvangurp.searchdsls.querydsl.SearchDSL
import com.jillesvangurp.searchdsls.querydsl.match
import de.sambalmueslie.openevent.core.account.api.Account
import de.sambalmueslie.openevent.core.search.api.CategorySearchRequest
import de.sambalmueslie.openevent.core.search.common.SearchQueryBuilder
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton

@Singleton
class CategorySearchQueryBuilder : SearchQueryBuilder<CategorySearchRequest> {
    override fun buildSearchQuery(
        pageable: Pageable,
        request: CategorySearchRequest,
        actor: Account
    ): (SearchDSL.() -> Unit) = {
        from = pageable.offset.toInt()
        resultSize = pageable.size
        trackTotalHits = "true"
        query = match(CategorySearchEntryData::name, request.fullTextSearch) {
            boost = 2.0
            lenient = true
            autoGenerateSynonymsPhraseQuery = true
        }
    }

}