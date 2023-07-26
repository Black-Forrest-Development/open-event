package de.sambalmueslie.openevent.core.logic

import de.sambalmueslie.openevent.TimeBasedTest
import de.sambalmueslie.openevent.core.model.AccountChangeRequest
import de.sambalmueslie.openevent.core.model.Announcement
import de.sambalmueslie.openevent.core.model.AnnouncementChangeRequest
import io.micronaut.data.model.Pageable
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@MicronautTest
class AnnouncementCrudServiceTest : TimeBasedTest() {
    @Inject
    lateinit var accountService: AccountCrudService

    @Inject
    lateinit var service: AnnouncementCrudService

    @Test
    fun announcementCrud() {
        val author = accountService.create(AccountChangeRequest("name", "first-name", "last-name", "email", ""))

        val request = AnnouncementChangeRequest("subject", "content")
        var announcement = service.create(author, request)

        assertEquals(request.subject, announcement.subject)
        assertEquals(request.content, announcement.content)
        assertEquals(author, announcement.author)
        assertTrue(announcement.id > 0)

        assertEquals(announcement, service.get(announcement.id))
        assertEquals(listOf(announcement), service.getAll(Pageable.from(0)).content)

        val update = AnnouncementChangeRequest("subject-update", "content-update")
        announcement = service.update(announcement.id, update)

        assertEquals(update.subject, announcement.subject)
        assertEquals(update.content, announcement.content)
        assertEquals(author, announcement.author)

        service.delete(announcement.id)
        assertEquals(emptyList<Announcement>(), service.getAll(Pageable.from(0)).content)

    }
}
