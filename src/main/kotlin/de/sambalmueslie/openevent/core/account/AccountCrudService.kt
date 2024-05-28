package de.sambalmueslie.openevent.core.account


import de.sambalmueslie.openevent.common.BaseCrudService
import de.sambalmueslie.openevent.core.*
import de.sambalmueslie.openevent.core.account.api.*
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class AccountCrudService(
    private val storage: AccountStorage,
    private val profileService: ProfileCrudService,
    private val preferencesService: PreferencesCrudService,
) : BaseCrudService<Long, Account, AccountChangeRequest, AccountChangeListener>(storage) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AccountCrudService::class.java)
        private const val SYSTEM_ACCOUNT = "system-account"
    }

    fun findByExternalId(externalId: String): Account? {
        return storage.findByExternalId(externalId)
    }

    fun findByName(name: String, pageable: Pageable): Page<Account> {
        return storage.findByName(name, pageable)
    }

    fun findByEmail(email: String): Account? {
        return storage.findByEmail(email)
    }

    fun validate(auth: Authentication, lang: String): AccountValidationResult {
        val email = auth.getEmail()
        val account = findExistingAccount(auth, email) ?: createNewAccount(auth)
        val profile = profileService.getForAccount(account) ?: createNewProfile(auth, account, lang)
        val info = AccountInfo.create(account, profile)
        return AccountValidationResult(true, account, profile, info)
    }

    private fun findExistingAccount(
        auth: Authentication,
        email: String
    ) = storage.findByExternalId(auth.getExternalId()) ?: storage.findByEmail(email)

    private fun createNewAccount(auth: Authentication) =
        create(getSystemAccount(), AccountChangeRequest(auth.getUsername(), "", auth.getExternalId()))

    private fun createNewProfile(auth: Authentication, account: Account, lang: String): Profile {
        val profileRequest = ProfileChangeRequest(
            auth.getEmail(),
            null, null,
            auth.getFirstName(),
            auth.getLastName(),
            null, null, null, null, lang
        )
        return profileService.merge(getSystemAccount(), account, profileRequest)
    }

    override fun create(actor: Account, request: AccountChangeRequest, properties: Map<String, Any>): Account {
        val account = super.create(actor, request, properties)
        profileService.handleAccountCreated(actor, account)
        preferencesService.handleAccountCreated(actor, account)
        return account
    }


    fun get(auth: Authentication): Account? {
        return findByEmail(auth.getEmail())
    }

    fun find(auth: Authentication): Account {
        val result = get(auth)
        require(result != null) { "Cannot find account for user" }
        return result
    }

    fun getSystemAccount(): Account {
        val existing = storage.findByExternalId(SYSTEM_ACCOUNT)
        if (existing != null) return existing

        val request = AccountChangeRequest(
            "system", "system",
            SYSTEM_ACCOUNT
        )
        return storage.createServiceAccount(request)
    }

    fun getProfile(account: Account): Profile? {
        return profileService.get(account.id)
    }

    fun getPreferences(account: Account): Preferences? {
        return preferencesService.get(account.id)
    }

    fun getInfos(pageable: Pageable): Page<AccountInfo> {
        return storage.getInfos(pageable)
    }

    fun getInfo(account: Account): AccountInfo {
        return storage.getInfo(account)
    }

}
