package de.sambalmueslie.openevent.core.category


import de.sambalmueslie.openevent.api.CategoryAPI
import de.sambalmueslie.openevent.api.CategoryAPI.Companion.PERMISSION_READ
import de.sambalmueslie.openevent.api.CategoryAPI.Companion.PERMISSION_WRITE
import de.sambalmueslie.openevent.core.CoreAPI
import de.sambalmueslie.openevent.core.account.AccountCrudService
import de.sambalmueslie.openevent.core.category.api.Category
import de.sambalmueslie.openevent.core.category.api.CategoryChangeRequest
import de.sambalmueslie.openevent.core.checkPermission
import de.sambalmueslie.openevent.infrastructure.audit.AuditService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.http.annotation.*
import io.micronaut.security.authentication.Authentication
import io.swagger.v3.oas.annotations.tags.Tag

@Controller("/api/category")
@Tag(name = "Category API")
@CoreAPI
class CategoryController(
    private val service: CategoryCrudService,
    private val accountService: AccountCrudService,
    audit: AuditService,
) : CategoryAPI {
    private val logger = audit.getLogger("Category API")

    @Get("/{id}")
    override fun get(auth: Authentication, id: Long): Category? {
        return auth.checkPermission(PERMISSION_READ) { service.get(id) }
    }

    @Get("/find/by/name")
    override fun findByName(auth: Authentication, @QueryValue name: String): Category? {
        return auth.checkPermission(PERMISSION_READ) { service.findByName(name) }
    }

    @Get()
    override fun getAll(auth: Authentication, pageable: Pageable): Page<Category> {
        return auth.checkPermission(PERMISSION_READ) { service.getAll(pageable) }
    }

    @Post()
    override fun create(auth: Authentication, @Body request: CategoryChangeRequest): Category {
        return auth.checkPermission(PERMISSION_WRITE) {
            logger.traceCreate(auth, request) { service.create(accountService.find(auth), request) }
        }
    }

    @Put("/{id}")
    override fun update(auth: Authentication, id: Long, @Body request: CategoryChangeRequest): Category {
        return auth.checkPermission(PERMISSION_WRITE) {
            logger.traceUpdate(auth, request) { service.update(accountService.find(auth), id, request) }
        }
    }

    @Delete("/{id}")
    override fun delete(auth: Authentication, id: Long): Category? {
        return auth.checkPermission(PERMISSION_WRITE) {
            logger.traceDelete(auth) { service.delete(accountService.find(auth), id) }
        }
    }

}
