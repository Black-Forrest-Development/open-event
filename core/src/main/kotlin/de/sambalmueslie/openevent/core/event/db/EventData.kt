package de.sambalmueslie.openevent.core.event.db

import de.sambalmueslie.openevent.common.crud.DataObject
import de.sambalmueslie.openevent.core.event.api.Event
import de.sambalmueslie.openevent.core.event.api.EventChangeRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Suppress("JpaObjectClassSignatureInspection")
@Entity(name = "Event")
@Table(name = "event")
data class EventData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long,
    @Column var title: String,
) : DataObject<Event> {

    companion object {
        fun create(request: EventChangeRequest, timestamp: LocalDateTime): EventData {
            TODO("not implemented yet")
        }
    }
    override fun convert() = Event(id, title)
    fun update(request: EventChangeRequest, timestamp: LocalDateTime): EventData {
        TODO("not implemented yet")
    }


}
