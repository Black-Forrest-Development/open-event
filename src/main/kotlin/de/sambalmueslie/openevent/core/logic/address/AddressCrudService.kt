package de.sambalmueslie.openevent.core.logic.address


import de.sambalmueslie.openevent.core.BaseCrudService
import de.sambalmueslie.openevent.core.logic.account.api.Account
import de.sambalmueslie.openevent.core.logic.address.api.Address
import de.sambalmueslie.openevent.core.logic.address.api.AddressChangeRequest
import de.sambalmueslie.openevent.core.logic.address.db.AddressStorage
import de.sambalmueslie.openevent.infrastructure.geo.GeoLocationResolver
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class AddressCrudService(
    private val storage: AddressStorage,
    private val geoAddressResolver: GeoLocationResolver
) : BaseCrudService<Long, Address, AddressChangeRequest, AddressChangeListener>(storage) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AddressCrudService::class.java)
    }

    fun create(actor: Account, account: Account, request: AddressChangeRequest): Address {
        val result = storage.create(resolveGeoAddress(request), account)
        notifyCreated(actor, result)
        return result
    }

    private fun resolveGeoAddress(request: AddressChangeRequest): AddressChangeRequest {
        val geoAddress = geoAddressResolver.get(request) ?: return request

        return AddressChangeRequest(
            request.street,
            request.streetNumber,
            request.zip,
            request.city,
            request.country,
            request.additionalInfo,
            geoAddress.lat,
            geoAddress.lon
        )
    }

    fun getAllForAccount(account: Account, pageable: Pageable): Page<Address> {
        return storage.findByAccount(account, pageable)
    }


}
