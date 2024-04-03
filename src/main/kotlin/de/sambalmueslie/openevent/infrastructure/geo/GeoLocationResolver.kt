package de.sambalmueslie.openevent.infrastructure.geo

import de.sambalmueslie.openevent.core.model.Address
import de.sambalmueslie.openevent.core.model.AddressChangeRequest
import de.sambalmueslie.openevent.core.model.Location
import de.sambalmueslie.openevent.core.model.LocationChangeRequest

interface GeoLocationResolver {
    fun get(location: Location): GeoLocation?
    fun get(address: Address): GeoLocation?
    fun get(request: LocationChangeRequest): GeoLocation?
    fun get(request: AddressChangeRequest): GeoLocation?
}
