package de.sambalmueslie.openevent.core.logic.preferences


import de.sambalmueslie.openevent.core.BaseCrudService
import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.core.model.Preferences
import de.sambalmueslie.openevent.core.model.PreferencesChangeRequest
import de.sambalmueslie.openevent.core.storage.PreferencesStorage
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class PreferencesCrudService(
    private val storage: PreferencesStorage
) : BaseCrudService<Long, Preferences, PreferencesChangeRequest, PreferencesChangeListener>(storage) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(PreferencesCrudService::class.java)
    }

    fun create(actor: Account, account: Account, request: PreferencesChangeRequest): Preferences {
        val result = storage.create(request, account)
        notifyCreated(actor, result)
        return result
    }

    fun getForAccount(account: Account): Preferences? {
        return storage.findByAccount(account)
    }


}
