package de.sambalmueslie.openevent.storage.address


import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.core.model.Address
import de.sambalmueslie.openevent.core.model.AddressChangeRequest
import de.sambalmueslie.openevent.core.storage.AddressStorage
import de.sambalmueslie.openevent.error.InvalidRequestException
import de.sambalmueslie.openevent.infrastructure.cache.CacheService
import de.sambalmueslie.openevent.infrastructure.time.TimeProvider
import de.sambalmueslie.openevent.storage.BaseStorageService
import de.sambalmueslie.openevent.storage.SimpleDataObjectConverter
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class AddressStorageService(
    private val repository: AddressRepository,
    cacheService: CacheService,
    private val timeProvider: TimeProvider,
) : BaseStorageService<Long, Address, AddressChangeRequest, AddressData>(
    repository,
    SimpleDataObjectConverter(),
    cacheService,
    Address::class,
    logger
), AddressStorage {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AddressStorageService::class.java)
        private const val ACCOUNT_REFERENCE = "account"
    }

    override fun create(request: AddressChangeRequest, account: Account): Address {
        return create(request, mapOf(Pair(ACCOUNT_REFERENCE, account)))
    }

    override fun createData(request: AddressChangeRequest, properties: Map<String, Any>): AddressData {
        val account = properties[ACCOUNT_REFERENCE] as? Account ?: throw InvalidRequestException("Cannot find account")
        return AddressData.create(account, request, timeProvider.now())
    }

    override fun updateData(data: AddressData, request: AddressChangeRequest): AddressData {
        return data.update(request, timeProvider.now())
    }

    override fun isValid(request: AddressChangeRequest) {
        // intentionally left empty
    }

    override fun findByAccount(account: Account, pageable: Pageable): Page<Address> {
        return repository.findByAccountId(account.id, pageable).map { it.convert() }
    }


}
