package de.sambalmueslie.openevent.core.history.api

import de.sambalmueslie.openevent.common.BusinessObjectChangeRequest
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class HistoryEntryChangeRequest(
    val type: HistoryEntryType,
    val message: String,
    val source: HistoryEntrySource,
    val info: String
) : BusinessObjectChangeRequest