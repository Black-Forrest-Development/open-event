package de.sambalmueslie.openevent.core.search.account

import de.sambalmueslie.openevent.core.account.api.AccountInfo
import kotlinx.serialization.Serializable

@Serializable
data class AccountSearchEntryData(
    var id: String,
    var name: String,
    var email: String?,

    var firstName: String,
    var lastName: String,
) {
    companion object {
        fun create(info: AccountInfo): AccountSearchEntryData {
            return AccountSearchEntryData(
                info.id.toString(),
                info.name,
                info.email.ifBlank { null },
                info.firstName,
                info.lastName
            )
        }
    }
}
