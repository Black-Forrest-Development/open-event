package de.sambalmueslie.openevent.core.logic.event.db

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Suppress("JpaMissingIdInspection")
@Entity(name = "EventAnnouncement")
@Table(name = "event_announcement")
data class EventAnnouncementRelation(
    @Column val eventId: Long,
    @Column val announcementId: Long,
)
