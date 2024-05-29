package de.sambalmueslie.openevent.core.registration

import de.sambalmueslie.openevent.core.registration.api.Registration

fun interface RegistrationSearchListener {
    fun updateSearch(registration: Registration)
}