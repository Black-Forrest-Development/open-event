package de.sambalmueslie.openevent.core.logic.announcement

import de.sambalmueslie.openevent.core.BusinessObjectChangeListener
import de.sambalmueslie.openevent.core.logic.announcement.api.Announcement

interface AnnouncementChangeListener : BusinessObjectChangeListener<Long, Announcement>
