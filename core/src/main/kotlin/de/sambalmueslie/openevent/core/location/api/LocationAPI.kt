package de.sambalmueslie.openevent.core.location.api

import de.sambalmueslie.openevent.common.crud.AuthCrudAPI

interface LocationAPI : AuthCrudAPI<Long, Location, LocationChangeRequest> {
    companion object {
        const val PERMISSION_READ = "location.read"
        const val PERMISSION_WRITE = "location.write"
    }
}
