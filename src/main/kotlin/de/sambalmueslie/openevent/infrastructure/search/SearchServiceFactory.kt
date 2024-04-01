package de.sambalmueslie.openevent.infrastructure.search

import de.sambalmueslie.openevent.config.AppConfig
import de.sambalmueslie.openevent.config.OpenSearchConfig
import de.sambalmueslie.openevent.error.InvalidRequestException
import io.micronaut.context.annotation.Factory
import jakarta.inject.Singleton

@Factory
class SearchServiceFactory(
    private val config: AppConfig,
    private val openSearch: OpenSearchConfig
) {

    @Singleton
    fun searchService(): SearchService {
        return when (config.searchEngine.lowercase()) {
            OpenSearchService.NAME -> OpenSearchService(openSearch)
//            else -> NopSearchService()
            else -> throw InvalidRequestException("no search service configured")
        }

    }

}