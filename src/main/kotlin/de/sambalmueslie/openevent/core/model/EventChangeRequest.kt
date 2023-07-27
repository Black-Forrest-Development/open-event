package de.sambalmueslie.openevent.core.model


import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest
import java.time.LocalDateTime

data class EventChangeRequest(
    val start: LocalDateTime,
    val finish: LocalDateTime,

    val title: String,
    val shortText: String,
    val longText: String,
    val imageUrl: String,
    val iconUrl: String,

    val location: LocationChangeRequest?,
    val registration: RegistrationChangeRequest
) : BusinessObjectChangeRequest
