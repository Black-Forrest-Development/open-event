package de.sambalmueslie.openevent.core.storage

import de.sambalmueslie.openevent.core.Storage
import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.core.model.Preferences
import de.sambalmueslie.openevent.core.model.PreferencesChangeRequest

interface PreferencesStorage : Storage<Long, Preferences, PreferencesChangeRequest> {
    fun create(request: PreferencesChangeRequest, account: Account): Preferences
    fun findByAccount(account: Account): Preferences?
}
