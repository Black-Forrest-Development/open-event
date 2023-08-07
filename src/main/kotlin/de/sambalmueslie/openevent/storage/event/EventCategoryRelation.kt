package de.sambalmueslie.openevent.storage.event

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Suppress("JpaMissingIdInspection")
@Entity(name = "EventCategory")
@Table(name = "event_category")
data class EventCategoryRelation(
    @Column val eventId: Long,
    @Column val categoryId: Long,
)
