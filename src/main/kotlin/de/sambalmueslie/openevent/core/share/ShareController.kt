package de.sambalmueslie.openevent.core.share

import de.sambalmueslie.openevent.api.EventAPI
import de.sambalmueslie.openevent.api.ShareAPI
import de.sambalmueslie.openevent.api.ShareAPI.Companion.PERMISSION_ADMIN
import de.sambalmueslie.openevent.api.ShareAPI.Companion.PERMISSION_READ
import de.sambalmueslie.openevent.api.ShareAPI.Companion.PERMISSION_WRITE
import de.sambalmueslie.openevent.common.PatchRequest
import de.sambalmueslie.openevent.core.account.AccountCrudService
import de.sambalmueslie.openevent.core.checkPermission
import de.sambalmueslie.openevent.core.getRealmRoles
import de.sambalmueslie.openevent.core.share.api.Share
import de.sambalmueslie.openevent.core.share.api.ShareChangeRequest
import de.sambalmueslie.openevent.core.share.api.SharedInfo
import de.sambalmueslie.openevent.infrastructure.audit.AuditService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.micronaut.security.annotation.Secured
import io.micronaut.security.authentication.Authentication
import io.micronaut.security.rules.SecurityRule
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/share")
@Tag(name = "Share API")
class ShareController(
    private val service: ShareCrudService,
    private val accountService: AccountCrudService,
    audit: AuditService,
) : ShareAPI {
    private val logger = audit.getLogger("Share API")

    @Get("/{id}")
    override fun get(auth: Authentication, id: String): Share? {
        return auth.checkPermission(PERMISSION_READ, PERMISSION_ADMIN) {
            service.get(id)
        }
    }


    @Get("/{id}/info")
    @Secured(SecurityRule.IS_ANONYMOUS)
    override fun getInfo(auth: Authentication?, id: String): SharedInfo? {
        if (auth == null) return service.getInfo(id, null)
        return auth.checkPermission(PERMISSION_READ, PERMISSION_ADMIN) {
            val account = accountService.get(auth)
            service.getInfo(id, account)
        }
    }

    @Get()
    override fun getAll(auth: Authentication, pageable: Pageable): Page<Share> {
        return auth.checkPermission(PERMISSION_READ, PERMISSION_ADMIN) {
            if (isAdmin(auth)) {
                service.getAll(pageable)
            } else {
                val account = accountService.get(auth) ?: return@checkPermission Page.empty()
                service.getAllForAccount(account, pageable)
            }
        }
    }

    @Post()
    override fun create(auth: Authentication, @Body request: ShareChangeRequest): Share {
        return auth.checkPermission(PERMISSION_WRITE, PERMISSION_ADMIN) {
            logger.traceCreate(auth, request) { service.create(accountService.find(auth), request) }
        }
    }

    @Put("/{id}")
    override fun update(auth: Authentication, id: String, @Body request: ShareChangeRequest): Share {
        return auth.checkPermission(PERMISSION_WRITE, PERMISSION_ADMIN) {
            logger.traceUpdate(auth, request) {
                service.update(accountService.find(auth), id, request)
            }
        }
    }

    @Delete("/{id}")
    override fun delete(auth: Authentication, id: String): Share? {
        return auth.checkPermission(PERMISSION_WRITE, PERMISSION_ADMIN) {
            logger.traceDelete(auth) { service.delete(accountService.find(auth), id) }
        }
    }


    @Put("/{id}/published")
    override fun setPublished(auth: Authentication, id: String, @Body value: PatchRequest<Boolean>): Share? {
        return auth.checkPermission(EventAPI.PERMISSION_WRITE, EventAPI.PERMISSION_ADMIN) {
            logger.traceAction(auth, "PUBLISHED", id.toString(), value) {
                service.setPublished(accountService.find(auth), id, value)
            }
        }
    }

    private fun isAdmin(auth: Authentication) = auth.getRealmRoles().contains(PERMISSION_ADMIN)
}