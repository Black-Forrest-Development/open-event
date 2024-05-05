package de.sambalmueslie.openevent.core.search.api

import de.sambalmueslie.openevent.core.account.api.Account
import io.micronaut.data.model.Page

data class AccountSearchResponse(
    override val result: Page<Account>
) : SearchResponse<Account>
