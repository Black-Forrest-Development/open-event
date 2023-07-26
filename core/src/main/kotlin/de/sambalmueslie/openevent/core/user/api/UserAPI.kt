package de.sambalmueslie.openevent.core.user.api

import de.sambalmueslie.openevent.common.crud.AuthCrudAPI

interface UserAPI : AuthCrudAPI<Long, User, UserChangeRequest> {
    companion object {
        const val PERMISSION_READ = "user.read"
        const val PERMISSION_WRITE = "user.write"
    }
}
