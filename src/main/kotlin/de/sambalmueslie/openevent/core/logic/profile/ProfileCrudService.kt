package de.sambalmueslie.openevent.core.logic.profile


import de.sambalmueslie.openevent.core.BaseCrudService
import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.core.model.Profile
import de.sambalmueslie.openevent.core.model.ProfileChangeRequest
import de.sambalmueslie.openevent.core.storage.ProfileStorage
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class ProfileCrudService(
    private val storage: ProfileStorage
) : BaseCrudService<Long, Profile, ProfileChangeRequest, ProfileChangeListener>(storage) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ProfileCrudService::class.java)
    }

    fun create(actor: Account, account: Account, request: ProfileChangeRequest): Profile {
        val result = storage.create(request, account)
        notifyCreated(actor, result)
        return result
    }

    fun getForAccount(account: Account): Profile? {
        return storage.findByAccount(account)
    }

    fun getForAccounts(accounts: Collection<Account>): List<Profile> {
        return storage.getForAccounts(accounts)
    }


}
