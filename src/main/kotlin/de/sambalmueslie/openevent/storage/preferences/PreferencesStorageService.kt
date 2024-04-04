package de.sambalmueslie.openevent.storage.preferences


import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.core.model.Preferences
import de.sambalmueslie.openevent.core.model.PreferencesChangeRequest
import de.sambalmueslie.openevent.core.storage.PreferencesStorage
import de.sambalmueslie.openevent.error.InvalidRequestException
import de.sambalmueslie.openevent.infrastructure.cache.CacheService
import de.sambalmueslie.openevent.infrastructure.time.TimeProvider
import de.sambalmueslie.openevent.storage.BaseStorageService
import de.sambalmueslie.openevent.storage.SimpleDataObjectConverter
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class PreferencesStorageService(
    private val repository: PreferencesRepository,
    cacheService: CacheService,
    private val timeProvider: TimeProvider,
) : BaseStorageService<Long, Preferences, PreferencesChangeRequest, PreferencesData>(
    repository,
    SimpleDataObjectConverter(),
    cacheService,
    Preferences::class,
    logger
), PreferencesStorage {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(PreferencesStorageService::class.java)
        private const val ACCOUNT_REFERENCE = "account"
    }

    override fun create(request: PreferencesChangeRequest, account: Account): Preferences {
        return create(request, mapOf(Pair(ACCOUNT_REFERENCE, account)))
    }

    override fun createData(request: PreferencesChangeRequest, properties: Map<String, Any>): PreferencesData {
        val account = properties[ACCOUNT_REFERENCE] as? Account ?: throw InvalidRequestException("Cannot find account")
        return PreferencesData.create(account, request, timeProvider.now())
    }

    override fun updateData(data: PreferencesData, request: PreferencesChangeRequest): PreferencesData {
        return data.update(request, timeProvider.now())
    }

    override fun isValid(request: PreferencesChangeRequest) {
        // intentionally left empty
    }


    override fun findByAccount(account: Account): Preferences? {
        return repository.findByAccountId(account.id)?.convert()
    }

}
