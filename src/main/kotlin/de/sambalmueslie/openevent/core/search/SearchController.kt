package de.sambalmueslie.openevent.core.search

import de.sambalmueslie.openevent.api.EventAPI.Companion.PERMISSION_READ
import de.sambalmueslie.openevent.api.SearchAPI
import de.sambalmueslie.openevent.api.SearchAPI.Companion.PERMISSION_ADMIN
import de.sambalmueslie.openevent.core.account.AccountCrudService
import de.sambalmueslie.openevent.core.checkPermission
import de.sambalmueslie.openevent.core.search.api.EventSearchRequest
import de.sambalmueslie.openevent.core.search.api.EventSearchResponse
import io.micronaut.data.model.Pageable
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Post
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/search")
@Tag(name = "Search API")
class SearchController(
    private val service: SearchService,
    private val accountService: AccountCrudService
) : SearchAPI {


    @Post("event")
    override fun searchEvents(
        auth: Authentication,
        @Body request: EventSearchRequest,
        pageable: Pageable
    ): EventSearchResponse {
        return auth.checkPermission(PERMISSION_READ) {
            service.searchEvents(
                accountService.find(auth),
                request,
                pageable
            )
        }
    }

    @Post("setup/event")
    fun setupEvents(auth: Authentication) {
        return auth.checkPermission(PERMISSION_ADMIN) {
            service.setupEvents()
            HttpResponse.created("")
        }
    }
}