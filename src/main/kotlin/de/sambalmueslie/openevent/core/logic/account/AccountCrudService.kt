package de.sambalmueslie.openevent.core.logic.account


import de.sambalmueslie.openevent.core.BaseCrudService
import de.sambalmueslie.openevent.core.auth.*
import de.sambalmueslie.openevent.core.logic.account.api.*
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

    fun validate(auth: Authentication): AccountValidationResult {
        val email = auth.getEmail()
        val existing = storage.findByExternalId(auth.getExternalId()) ?: storage.findByEmail(email)

        if (existing != null) return AccountValidationResult(false, existing)

        val system = getSystemAccount()
        val account = create(system, AccountChangeRequest(auth.getUsername(), "", auth.getExternalId()))

        val profileRequest = ProfileChangeRequest(
            auth.getEmail(),
            null, null,
            auth.getFirstName(),
            auth.getLastName(),
            null, null, null, null
        )
        profileService.merge(system, account, profileRequest)

        return AccountValidationResult(true, account)
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

        val request = AccountChangeRequest("system", "system", SYSTEM_ACCOUNT)
        return storage.createServiceAccount(request)
    }

    fun getProfile(account: Account): Profile? {
        return profileService.get(account.id)
    }

    fun getPreferences(account: Account): Preferences? {
        return preferencesService.get(account.id)
    }


}
