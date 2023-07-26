package de.sambalmueslie.openevent.core.group.api

import de.sambalmueslie.openevent.common.crud.AuthCrudAPI

interface GroupAPI : AuthCrudAPI<Long, Group, GroupChangeRequest> {
    companion object {
        const val PERMISSION_READ = "group.read"
        const val PERMISSION_WRITE = "group.write"
    }
}
