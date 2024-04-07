package de.sambalmueslie.openevent.core.logic.event

import de.sambalmueslie.openevent.core.BusinessObjectChangeListener
import de.sambalmueslie.openevent.core.logic.account.api.Account
import de.sambalmueslie.openevent.core.logic.event.api.Event

interface EventChangeListener : BusinessObjectChangeListener<Long, Event> {
    fun publishedChanged(actor: Account, event: Event)
}
