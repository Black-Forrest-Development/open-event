package de.sambalmueslie.openevent.gateway.app.account

import de.sambalmueslie.openevent.core.account.AccountCrudService
import de.sambalmueslie.openevent.core.account.api.*
import de.sambalmueslie.openevent.core.checkPermission
import de.sambalmueslie.openevent.infrastructure.audit.AuditService
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/app/account")
@Tag(name = "APP Account API")
class AccountController(
    private val service: AccountCrudService,
    audit: AuditService,
) {
    companion object {
        private const val PERMISSION_READ = "account.read"
        private const val PERMISSION_WRITE = "account.write"
    }

    private val logger = audit.getLogger("APP Account API")

    @Get()
    fun get(auth: Authentication): Account? {
        return auth.checkPermission(PERMISSION_READ) { service.get(auth) }
    }


    @Put()
    fun update(auth: Authentication, @Body request: AccountChangeRequest): Account {
        return auth.checkPermission(PERMISSION_WRITE) {
            val account = service.get(auth)
            if (account == null) {
                val systemAccount = service.getSystemAccount()
                logger.traceCreate(auth, request) { service.create(systemAccount, request) }
            } else {
                logger.traceUpdate(auth, request) {
                    service.update(service.find(auth), account.id, request)
                }
            }
        }
    }

    @Get("/validate")
    fun validate(auth: Authentication, @QueryValue lang: String): AccountValidationResult {
        return auth.checkPermission(PERMISSION_READ) { service.validate(auth, lang) }
    }

    @Get("/profile")
    fun getProfile(auth: Authentication): Profile? {
        return auth.checkPermission(PERMISSION_READ) {
            val account = service.get(auth) ?: return@checkPermission null
            service.getProfile(account)
        }
    }

    @Get("/preferences")
    fun getPreferences(auth: Authentication): Preferences? {
        return auth.checkPermission(PERMISSION_READ) {
            val account = service.get(auth) ?: return@checkPermission null
            service.getPreferences(account)
        }
    }
}