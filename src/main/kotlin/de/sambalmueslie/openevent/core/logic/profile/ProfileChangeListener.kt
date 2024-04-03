package de.sambalmueslie.openevent.core.logic.profile

import de.sambalmueslie.openevent.core.BusinessObjectChangeListener
import de.sambalmueslie.openevent.core.model.Profile

interface ProfileChangeListener : BusinessObjectChangeListener<Long, Profile>
