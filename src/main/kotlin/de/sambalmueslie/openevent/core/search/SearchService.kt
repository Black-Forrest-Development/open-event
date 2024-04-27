package de.sambalmueslie.openevent.core.search

import de.sambalmueslie.openevent.core.account.api.Account
import de.sambalmueslie.openevent.core.search.api.EventSearchRequest
import de.sambalmueslie.openevent.core.search.api.EventSearchResponse
import de.sambalmueslie.openevent.core.search.operator.EventSearchOperator
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class SearchService(
    private val eventOperator: EventSearchOperator
) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(SearchService::class.java)
    }

    fun searchEvents(actor: Account, request: EventSearchRequest, pageable: Pageable): EventSearchResponse {
        return eventOperator.search(actor, request, pageable)
    }

    fun setupEvents() {
        return eventOperator.setup()
    }
}