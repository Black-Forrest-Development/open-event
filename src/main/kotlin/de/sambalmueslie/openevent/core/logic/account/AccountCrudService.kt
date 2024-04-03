package de.sambalmueslie.openevent.core.logic.account


import de.sambalmueslie.openevent.core.BaseCrudService
import de.sambalmueslie.openevent.core.auth.*
import de.sambalmueslie.openevent.core.logic.profile.ProfileCrudService
import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.core.model.AccountChangeRequest
import de.sambalmueslie.openevent.core.model.AccountValidationResult
import de.sambalmueslie.openevent.core.model.ProfileChangeRequest
import de.sambalmueslie.openevent.core.storage.AccountStorage
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.time.LocalDate

@Singleton
class AccountCrudService(
    private val storage: AccountStorage,
    private val profileService: ProfileCrudService
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
        val existing = storage.findByEmail(email)

        if (existing != null) return AccountValidationResult(false, existing)

        val result = storage.create(AccountChangeRequest(auth.getUsername(), "", auth.getExternalId()))
        notifyCreated(result, result)

        val profile = profileService.getForAccount(result)
        val request = ProfileChangeRequest(
            auth.getEmail(),
            profile?.phone ?: "",
            profile?.mobile ?: "",
            auth.getFirstName(),
            auth.getLastName(),
            profile?.dateOfBirth ?: LocalDate.MIN,
            profile?.gender ?: "",
            profile?.profilePicture ?: "",
            profile?.website ?: "",
        )

        if (profile == null) {
            profileService.create(result, result, request)
        } else {
            profileService.update(result, profile.id, request)
        }

        return AccountValidationResult(true, result)
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
        val existing = storage.findByEmail(SYSTEM_ACCOUNT)
        if (existing != null) return existing

        val request = AccountChangeRequest("system", "system", SYSTEM_ACCOUNT)
        return storage.createServiceAccount(request)
    }


}
