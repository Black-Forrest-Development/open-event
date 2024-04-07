package de.sambalmueslie.openevent.core.logic.account

import de.sambalmueslie.openevent.core.BusinessObjectChangeListener
import de.sambalmueslie.openevent.core.logic.account.api.Preferences

interface PreferencesChangeListener : BusinessObjectChangeListener<Long, Preferences>
