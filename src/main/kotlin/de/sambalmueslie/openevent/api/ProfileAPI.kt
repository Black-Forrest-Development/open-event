package de.sambalmueslie.openevent.api

import de.sambalmueslie.openevent.core.model.Profile
import de.sambalmueslie.openevent.core.model.ProfileChangeRequest
import io.micronaut.security.authentication.Authentication

interface ProfileAPI : CrudAPI<Long, Profile, ProfileChangeRequest> {
    companion object {
        const val PERMISSION_READ = "openevent.profile.read"
        const val PERMISSION_WRITE = "openevent.profile.write"
        const val PERMISSION_ADMIN = "openevent.profile.admin"
    }

    fun getOwn(auth: Authentication): Profile?

}
