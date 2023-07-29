package de.sambalmueslie.openevent.core.logic

import de.sambalmueslie.openevent.core.Storage
import de.sambalmueslie.openevent.core.model.*
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

interface EventStorage : Storage<Long, Event, EventChangeRequest> {
    fun create(request: EventChangeRequest, owner: Account): Event

    fun assign(event: Event, category: Category)
    fun revoke(event: Event, category: Category)
    fun getCategories(event: Event, pageable: Pageable): Page<Category>

    fun assign(event: Event, announcement: Announcement)
    fun revoke(event: Event, announcement: Announcement)
    fun getAnnouncements(event: Event, pageable: Pageable): Page<Announcement>

}
