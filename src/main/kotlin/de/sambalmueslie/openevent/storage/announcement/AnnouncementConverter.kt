package de.sambalmueslie.openevent.storage.announcement


import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.core.model.Announcement
import de.sambalmueslie.openevent.error.InconsistentDataException
import de.sambalmueslie.openevent.storage.DataObjectConverter
import de.sambalmueslie.openevent.storage.user.AccountStorageService
import io.micronaut.data.model.Page
import jakarta.inject.Singleton

@Singleton
class AnnouncementConverter(
    private val userService: AccountStorageService,
) : DataObjectConverter<Announcement, AnnouncementData> {

    override fun convert(obj: AnnouncementData): Announcement {
        return convert(obj, userService.get(obj.authorId))
    }

    override fun convert(objs: List<AnnouncementData>): List<Announcement> {
        val authorIds = objs.map { it.authorId }.toSet()
        val author = userService.getByIds(authorIds).associateBy { it.id }
        return objs.map { convert(it, author[it.id]) }
    }

    override fun convert(page: Page<AnnouncementData>): Page<Announcement> {
        val authorIds = page.content.map { it.authorId }.toSet()
        val author = userService.getByIds(authorIds).associateBy { it.id }
        return page.map { convert(it, author[it.id]) }
    }

    private fun convert(data: AnnouncementData, author: Account?): Announcement {
        if (author == null) throw InconsistentDataException("Cannot find author for announcement")
        return data.convert(author)
    }
}
