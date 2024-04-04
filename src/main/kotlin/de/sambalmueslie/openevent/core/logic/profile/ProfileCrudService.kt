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

    fun handleAccountCreated(actor: Account, account: Account) {
        val existing = storage.findByAccount(account)
        if (existing != null) return

        val request = ProfileChangeRequest(
            null,
            null,
            null,
            "",
            "",
            null,
            null,
            null,
            null,
        )

        create(actor, account, request)
    }

    fun merge(actor: Account, account: Account, request: ProfileChangeRequest): Profile {
        val profile = storage.findByAccount(account)

        val profileRequest = ProfileChangeRequest(
            profile?.email ?: request.email,
            profile?.phone ?: request.phone,
            profile?.mobile ?: request.mobile,
            profile?.firstName.isNullOrBlank().let { request.firstName },
            profile?.lastName.isNullOrBlank().let { request.lastName },
            profile?.dateOfBirth ?: request.dateOfBirth,
            profile?.gender ?: request.gender,
            profile?.profilePicture ?: request.profilePicture,
            profile?.website ?: request.website,
        )

        return if (profile == null) {
            create(account, account, profileRequest)
        } else {
            update(account, profile.id, profileRequest)
        }
    }


}
