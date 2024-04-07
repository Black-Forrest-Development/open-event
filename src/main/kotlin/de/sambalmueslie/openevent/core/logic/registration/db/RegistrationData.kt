package de.sambalmueslie.openevent.core.logic.registration.db


import de.sambalmueslie.openevent.core.logic.event.api.Event
import de.sambalmueslie.openevent.core.logic.registration.api.Registration
import de.sambalmueslie.openevent.core.logic.registration.api.RegistrationChangeRequest
import de.sambalmueslie.openevent.storage.SimpleDataObject
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "Registration")
@Table(name = "registration")
data class RegistrationData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,

    @Column var eventId: Long,

    @Column var maxGuestAmount: Int,
    @Column var interestedAllowed: Boolean,
    @Column var ticketsEnabled: Boolean,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) : SimpleDataObject<Registration> {
    companion object {
        fun create(
            event: Event,
            request: RegistrationChangeRequest,
            timestamp: LocalDateTime
        ): RegistrationData {
            return RegistrationData(
                0,
                event.id,
                request.maxGuestAmount,
                request.interestedAllowed,
                request.ticketsEnabled,
                timestamp
            )
        }
    }

    override fun convert(): Registration {
        return Registration(id, eventId, maxGuestAmount, interestedAllowed, ticketsEnabled)
    }

    fun update(request: RegistrationChangeRequest, timestamp: LocalDateTime): RegistrationData {
        maxGuestAmount = request.maxGuestAmount
        interestedAllowed = request.interestedAllowed
        ticketsEnabled = request.ticketsEnabled
        updated = timestamp
        return this
    }
}


