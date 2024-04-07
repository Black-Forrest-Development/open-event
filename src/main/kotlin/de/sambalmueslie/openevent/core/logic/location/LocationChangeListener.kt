package de.sambalmueslie.openevent.core.logic.location

import de.sambalmueslie.openevent.core.BusinessObjectChangeListener
import de.sambalmueslie.openevent.core.logic.location.api.Location

interface LocationChangeListener : BusinessObjectChangeListener<Long, Location>
