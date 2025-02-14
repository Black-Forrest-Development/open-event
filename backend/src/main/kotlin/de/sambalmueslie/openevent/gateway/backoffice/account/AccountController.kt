package de.sambalmueslie.openevent.gateway.backoffice.account

import de.sambalmueslie.openevent.core.account.AccountCrudService
import de.sambalmueslie.openevent.core.account.api.Account
import de.sambalmueslie.openevent.core.account.api.AccountValidationResult
import de.sambalmueslie.openevent.core.account.api.Preferences
import de.sambalmueslie.openevent.core.account.api.Profile
import de.sambalmueslie.openevent.core.checkPermission
import de.sambalmueslie.openevent.infrastructure.audit.AuditService
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.http.annotation.QueryValue
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/backoffice/account")
@Tag(name = "BACKOFFICE Account API")
class AccountController(
    private val service: AccountCrudService,
    audit: AuditService,
) {
    companion object {
        private const val PERMISSION_READ = "account.read"
        private const val PERMISSION_WRITE = "account.write"
        private const val PERMISSION_ADMIN = "account.admin"
    }

    private val logger = audit.getLogger("BACKOFFICE Account API")

    @Get()
    fun get(auth: Authentication): Account? {
        return auth.checkPermission(PERMISSION_ADMIN) { service.get(auth) }
    }

    @Get("/validate")
    fun validate(auth: Authentication, @QueryValue lang: String): AccountValidationResult {
        return auth.checkPermission(PERMISSION_ADMIN) { service.validate(auth, lang) }
    }

    @Get("/profile")
    fun getProfile(auth: Authentication): Profile? {
        return auth.checkPermission(PERMISSION_ADMIN) {
            val account = service.get(auth) ?: return@checkPermission null
            service.getProfile(account)
        }
    }

    @Get("/preferences")
    fun getPreferences(auth: Authentication): Preferences? {
        return auth.checkPermission(PERMISSION_ADMIN) {
            val account = service.get(auth) ?: return@checkPermission null
            service.getPreferences(account)
        }
    }
}