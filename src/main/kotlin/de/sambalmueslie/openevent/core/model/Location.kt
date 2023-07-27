package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObject

data class Location(
    override val id: Long,

    val street: String,
    val streetNumber: String,
    val zip: String,
    val city: String,
    val country: String,
    val additionalInfo: String,

    val lat: Double,
    val lon: Double,

    val size: Int

) : BusinessObject<Long>
