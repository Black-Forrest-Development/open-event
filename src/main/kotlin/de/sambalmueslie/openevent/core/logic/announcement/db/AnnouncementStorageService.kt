package de.sambalmueslie.openevent.core.logic.announcement.db


import de.sambalmueslie.openevent.core.logic.account.api.Account
import de.sambalmueslie.openevent.core.logic.announcement.api.Announcement
import de.sambalmueslie.openevent.core.logic.announcement.api.AnnouncementChangeRequest
import de.sambalmueslie.openevent.error.InvalidRequestException
import de.sambalmueslie.openevent.infrastructure.cache.CacheService
import de.sambalmueslie.openevent.infrastructure.time.TimeProvider
import de.sambalmueslie.openevent.storage.BaseStorageService
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class AnnouncementStorageService(
    private val repository: AnnouncementRepository,
    private val converter: AnnouncementConverter,
    cacheService: CacheService,
    private val timeProvider: TimeProvider,
) : BaseStorageService<Long, Announcement, AnnouncementChangeRequest, AnnouncementData>(
    repository,
    converter,
    cacheService,
    Announcement::class,
    logger
), AnnouncementStorage {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AnnouncementStorageService::class.java)
        private const val AUTHOR_REFERENCE = "author"
    }

    override fun create(request: AnnouncementChangeRequest, author: Account): Announcement {
        return create(request, mapOf(Pair(AUTHOR_REFERENCE, author)))
    }

    override fun createData(request: AnnouncementChangeRequest, properties: Map<String, Any>): AnnouncementData {
        val account = properties[AUTHOR_REFERENCE] as? Account ?: throw InvalidRequestException("Cannot find account")
        return AnnouncementData.create(account, request, timeProvider.now())
    }

    override fun isValid(request: AnnouncementChangeRequest) {
        if (request.subject.isBlank()) throw InvalidRequestException("Subject cannot be blank")
    }

    override fun updateData(data: AnnouncementData, request: AnnouncementChangeRequest): AnnouncementData {
        return data.update(request, timeProvider.now())
    }

}
