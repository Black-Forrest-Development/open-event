package de.sambalmueslie.openevent.core.logic.event.api

import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest
import de.sambalmueslie.openevent.core.logic.account.api.Account

data class EventCreateRequest(
    val account: Account,
    val content: EventChangeRequest
): BusinessObjectChangeRequest
