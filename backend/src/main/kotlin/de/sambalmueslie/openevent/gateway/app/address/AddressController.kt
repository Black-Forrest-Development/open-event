package de.sambalmueslie.openevent.gateway.app.address

import de.sambalmueslie.openevent.core.account.AccountCrudService
import de.sambalmueslie.openevent.core.address.AddressCrudService
import de.sambalmueslie.openevent.core.address.api.Address
import de.sambalmueslie.openevent.core.checkPermission
import de.sambalmueslie.openevent.infrastructure.audit.AuditService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/app/address")
@Tag(name = "APP Address API")
class AddressController(
    private val service: AddressCrudService,
    private val accountService: AccountCrudService,
    audit: AuditService,
) {
    companion object {
        private const val PERMISSION_READ = "address.read"
        private const val PERMISSION_WRITE = "address.write"
    }


    private val logger = audit.getLogger("APP Address API")

    @Get()
    fun get(auth: Authentication, pageable: Pageable): Page<Address> {
        return auth.checkPermission(PERMISSION_READ, PERMISSION_WRITE) {
            val account = accountService.get(auth) ?: return@checkPermission Page.empty()
            service.getAllForAccount(account, pageable)
        }
    }

}