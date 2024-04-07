package de.sambalmueslie.openevent.infrastructure.geo

import de.sambalmueslie.openevent.core.logic.address.api.Address
import de.sambalmueslie.openevent.core.logic.address.api.AddressChangeRequest
import de.sambalmueslie.openevent.core.logic.location.api.Location
import de.sambalmueslie.openevent.core.logic.location.api.LocationChangeRequest

interface GeoLocationResolver {
    fun get(location: Location): GeoLocation?
    fun get(address: Address): GeoLocation?
    fun get(request: LocationChangeRequest): GeoLocation?
    fun get(request: AddressChangeRequest): GeoLocation?
}
