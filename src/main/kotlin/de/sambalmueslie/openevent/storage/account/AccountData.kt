package de.sambalmueslie.openevent.storage.account

import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.core.model.AccountChangeRequest
import de.sambalmueslie.openevent.storage.SimpleDataObject
import jakarta.persistence.*
import java.time.LocalDateTime

@Entity(name = "Account")
@Table(name = "account")
data class AccountData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Column var externalId: String?,
    @Column var name: String,
    @Column var iconUrl: String,
    @Column var lastLoginDate: LocalDateTime?,

    @Column var serviceAccount: Boolean,
    @Column var idpLinked: Boolean,

    @Column var lastSync: LocalDateTime = LocalDateTime.now(),
    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) : SimpleDataObject<Account> {

    companion object {
        fun create(
            request: AccountChangeRequest,
            serviceAccount: Boolean,
            idpLinked: Boolean,
            timestamp: LocalDateTime
        ): AccountData {
            return AccountData(
                0,
                request.externalId,
                request.name,
                request.iconUrl,
                timestamp,
                serviceAccount,
                idpLinked,
                timestamp
            )
        }
    }

    override fun convert(): Account {
        return Account(id, externalId, name, iconUrl, created, lastLoginDate, serviceAccount, idpLinked)
    }

    fun update(request: AccountChangeRequest, timestamp: LocalDateTime): AccountData {
        name = request.name
        iconUrl = request.iconUrl
        updated = timestamp
        return this
    }

    fun login(timestamp: LocalDateTime): AccountData {
        lastLoginDate = timestamp
        return this
    }
}
