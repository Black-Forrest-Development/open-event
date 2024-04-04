package de.sambalmueslie.openevent.core.logic.profile

import de.sambalmueslie.openevent.core.logic.account.AccountChangeListener
import de.sambalmueslie.openevent.core.logic.account.AccountCrudService
import de.sambalmueslie.openevent.core.model.Account
import io.micronaut.context.annotation.Context

@Context
class AccountChangeHandler(
    accountService: AccountCrudService,
    private val service: ProfileCrudService
) : AccountChangeListener {
    init {
        accountService.register(this)
    }
    override fun handleCreated(actor: Account, obj: Account) {
        service.handleAccountCreated(actor, obj)
    }
}