package de.sambalmueslie.openevent.core.logic.account.api

import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class AccountChangeRequest(
    val name: String,
    val iconUrl: String,
    val externalId: String?,
) : BusinessObjectChangeRequest
