package de.sambalmueslie.openevent.api

import de.sambalmueslie.openevent.core.address.api.Address
import de.sambalmueslie.openevent.core.address.api.AddressChangeRequest

interface AddressAPI : CrudAPI<Long, Address, AddressChangeRequest> {
    companion object {
        const val PERMISSION_READ = "openevent.address.read"
        const val PERMISSION_WRITE = "openevent.address.write"
        const val PERMISSION_ADMIN = "openevent.address.admin"
    }

}
