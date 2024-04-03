package de.sambalmueslie.openevent.storage.preferences

import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.core.model.Preferences
import de.sambalmueslie.openevent.core.model.PreferencesChangeRequest
import de.sambalmueslie.openevent.storage.SimpleDataObject
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "Preferences")
@Table(name = "preferences")
data class PreferencesData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column var notifyOnEventChanges: Boolean,

    @Column var accountId: Long,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) : SimpleDataObject<Preferences> {

    companion object {
        fun create(
            account: Account,
            request: PreferencesChangeRequest,
            timestamp: LocalDateTime
        ): PreferencesData {
            return PreferencesData(
                0,
                request.notifyOnEventChanges,
                account.id,
                timestamp
            )
        }
    }

    override fun convert(): Preferences {
        return Preferences(id, notifyOnEventChanges)
    }

    fun update(request: PreferencesChangeRequest, timestamp: LocalDateTime): PreferencesData {
        notifyOnEventChanges = request.notifyOnEventChanges
        updated = timestamp
        return this
    }
}
