package de.sambalmueslie.openevent.core.logic.export

import de.sambalmueslie.openevent.core.logic.category.api.Category
import de.sambalmueslie.openevent.core.logic.event.api.Event
import de.sambalmueslie.openevent.core.logic.location.api.Location
import de.sambalmueslie.openevent.core.logic.registration.api.RegistrationInfo

data class EventPdfContent(
    val event: Event,
    val location: Location,
    val registration: RegistrationInfo,
    val categories: List<Category>,
    val qrCode: String,
    val availableSpace: List<Char>
)
