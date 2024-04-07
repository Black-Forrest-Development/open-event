package de.sambalmueslie.openevent.core.logic.history.db

import de.sambalmueslie.openevent.core.Storage
import de.sambalmueslie.openevent.core.logic.account.api.Account
import de.sambalmueslie.openevent.core.logic.event.api.Event
import de.sambalmueslie.openevent.core.logic.history.api.HistoryEntry
import de.sambalmueslie.openevent.core.logic.history.api.HistoryEntryChangeRequest
import de.sambalmueslie.openevent.core.logic.history.api.HistoryEntrySource
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import java.time.LocalDateTime

interface HistoryEntryStorage : Storage<Long, HistoryEntry, HistoryEntryChangeRequest> {
    fun create(request: HistoryEntryChangeRequest, event: Event, actor: Account): HistoryEntry
    fun create(request: HistoryEntryChangeRequest, event: Event, actor: Account, timestamp: LocalDateTime): HistoryEntry
    fun findByEvent(event: Event, pageable: Pageable): Page<HistoryEntry>

    fun findByEventAndActorOrSource(
        event: Event,
        account: Account,
        source: Set<HistoryEntrySource>,
        pageable: Pageable
    ): Page<HistoryEntry>

    fun getAllForAccountOrSource(
        account: Account,
        source: Set<HistoryEntrySource>,
        pageable: Pageable
    ): Page<HistoryEntry>

    fun deleteAll()
}
