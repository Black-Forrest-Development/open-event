package de.sambalmueslie.openevent.api

import de.sambalmueslie.openevent.core.logic.account.api.Profile
import de.sambalmueslie.openevent.core.logic.account.api.ProfileChangeRequest

interface ProfileAPI : CrudAPI<Long, Profile, ProfileChangeRequest> {
    companion object {
        const val PERMISSION_READ = "openevent.profile.read"
        const val PERMISSION_WRITE = "openevent.profile.write"
        const val PERMISSION_ADMIN = "openevent.profile.admin"
    }

}
