package de.sambalmueslie.openevent.core.event

import de.sambalmueslie.openevent.core.event.api.EventInfo

fun interface EventSearchListener {
    fun updateSearch(event: EventInfo)
}