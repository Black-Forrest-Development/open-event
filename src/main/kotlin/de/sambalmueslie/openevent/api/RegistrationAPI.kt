package de.sambalmueslie.openevent.api

import de.sambalmueslie.openevent.core.model.Registration
import de.sambalmueslie.openevent.core.model.RegistrationChangeRequest

interface RegistrationAPI : CrudAPI<Long, Registration, RegistrationChangeRequest> {
    companion object {
        const val PERMISSION_READ = "openevent.registration.read"
        const val PERMISSION_WRITE = "openevent.registration.write"
    }

}
