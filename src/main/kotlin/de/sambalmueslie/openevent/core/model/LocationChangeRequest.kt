package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest

data class LocationChangeRequest(
    val street: String,
    val streetNumber: String,
    val zip: String,
    val city: String,
    val country: String,
    val additionalInfo: String,

    val lat: Double,
    val lon: Double,

    val size: Int
) : BusinessObjectChangeRequest
