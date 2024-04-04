package de.sambalmueslie.openevent.core.logic.preferences

import de.sambalmueslie.openevent.core.BusinessObjectChangeListener
import de.sambalmueslie.openevent.core.model.Preferences

interface PreferencesChangeListener : BusinessObjectChangeListener<Long, Preferences>
