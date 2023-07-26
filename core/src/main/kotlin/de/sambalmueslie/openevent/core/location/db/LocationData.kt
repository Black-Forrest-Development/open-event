package de.sambalmueslie.openevent.core.location.db

import de.sambalmueslie.openevent.common.crud.RelationalDataObject
import de.sambalmueslie.openevent.core.location.api.Location
import de.sambalmueslie.openevent.core.location.api.LocationChangeRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Suppress("JpaObjectClassSignatureInspection")
@Entity(name = "Location")
@Table(name = "location")
data class LocationData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long,
    @Column
    var capacity: Int,
    @Column
    var created: LocalDateTime,
    @Column
    var updated: LocalDateTime?,
) : RelationalDataObject<Location> {
    companion object {
        fun create(request: LocationChangeRequest, timestamp: LocalDateTime) = LocationData(0, request.capacity, timestamp, null)
    }

    fun update(request: LocationChangeRequest, timestamp: LocalDateTime): LocationData {
        capacity = request.capacity
        updated = timestamp
        return this
    }
}
