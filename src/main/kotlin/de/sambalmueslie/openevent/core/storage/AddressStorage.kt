package de.sambalmueslie.openevent.core.storage

import de.sambalmueslie.openevent.core.Storage
import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.core.model.Address
import de.sambalmueslie.openevent.core.model.AddressChangeRequest
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

interface AddressStorage : Storage<Long, Address, AddressChangeRequest> {
    fun create(request: AddressChangeRequest, account: Account): Address
    fun findByAccount(account: Account, pageable: Pageable): Page<Address>
}
