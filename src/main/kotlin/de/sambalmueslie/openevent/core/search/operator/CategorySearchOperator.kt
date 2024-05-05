package de.sambalmueslie.openevent.core.search.operator

import com.jillesvangurp.ktsearch.ids
import com.jillesvangurp.ktsearch.search
import com.jillesvangurp.ktsearch.total
import com.jillesvangurp.searchdsls.querydsl.SearchDSL
import com.jillesvangurp.searchdsls.querydsl.match
import de.sambalmueslie.openevent.core.account.api.Account
import de.sambalmueslie.openevent.core.category.CategoryChangeListener
import de.sambalmueslie.openevent.core.category.CategoryCrudService
import de.sambalmueslie.openevent.core.category.api.Category
import de.sambalmueslie.openevent.core.search.api.CategorySearchRequest
import de.sambalmueslie.openevent.core.search.api.CategorySearchResponse
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
open class CategorySearchOperator(
    private val service: CategoryCrudService,
    openSearch: OpenSearchService
) : BaseOpenSearchOperator<Category, CategorySearchRequest, CategorySearchResponse>(openSearch, "oe.category", logger) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CategorySearchOperator::class.java)
    }

    init {
        service.register(object : CategoryChangeListener {
            override fun handleCreated(actor: Account, obj: Category) {
                handleChanged(obj)
            }

            override fun handleUpdated(actor: Account, obj: Category) {
                handleChanged(obj)
            }

            override fun handleDeleted(actor: Account, obj: Category) {
                deleteDocument(obj.id.toString())
            }
        })
    }

    private fun handleChanged(category: Category) {
        val data = convert(category)
        updateDocument(data)
    }


    override fun createMappings() = CategorySearchEntryData.createMappings()

    override fun initialLoadPage(pageable: Pageable): Page<Pair<String, String>> {
        val page = service.getAll(pageable)
        return page.map { convert(it) }
    }

    private fun convert(obj: Category): Pair<String, String> {
        val input = CategorySearchEntryData.create(obj)
        return Pair(input.id, mapper.writeValueAsString(input))
    }


    override fun search(actor: Account, request: CategorySearchRequest, pageable: Pageable): CategorySearchResponse {
        val response = runBlocking {
            client.search(name, block = buildSearchQuery(pageable, request, actor))
        }

        val ids = response.ids.map { it.toLong() }.toSet()
        val result = service.getByIds(ids)

        return CategorySearchResponse(Page.of(result, pageable, response.total))
    }

    private fun buildSearchQuery(
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