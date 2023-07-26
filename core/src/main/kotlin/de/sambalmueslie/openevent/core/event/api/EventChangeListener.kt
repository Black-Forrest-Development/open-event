package de.sambalmueslie.openevent.core.event.api

import de.sambalmueslie.openevent.common.crud.BusinessObjectChangeListener

interface EventChangeListener : BusinessObjectChangeListener<Long, Event>
