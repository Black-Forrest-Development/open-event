package de.sambalmueslie.openevent.core.logic.announcement.db

import de.sambalmueslie.openevent.core.Storage
import de.sambalmueslie.openevent.core.logic.account.api.Account
import de.sambalmueslie.openevent.core.logic.announcement.api.Announcement
import de.sambalmueslie.openevent.core.logic.announcement.api.AnnouncementChangeRequest

interface AnnouncementStorage : Storage<Long, Announcement, AnnouncementChangeRequest> {
    fun create(request: AnnouncementChangeRequest, author: Account): Announcement
}
