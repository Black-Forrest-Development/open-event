package de.sambalmueslie.openevent.core.logic.account.api

import de.sambalmueslie.openevent.core.BusinessObject
import de.sambalmueslie.openevent.core.logic.account.db.AccountData

data class AccountInfo(
    override val id: Long,
    val name: String,
    val iconUrl: String,
    val email: String,
    val firstName: String,
    val lastName: String,
) : BusinessObject<Long> {
    companion object {
        fun create(account: Account, profile: Profile?): AccountInfo {
            return AccountInfo(
                account.id,
                account.name,
                account.iconUrl,
                profile?.email ?: "",
                profile?.firstName ?: "",
                profile?.lastName ?: "",
            )
        }

        fun create(account: AccountData, profile: Profile?): AccountInfo {
            return AccountInfo(
                account.id,
                account.name,
                account.iconUrl,
                profile?.email ?: "",
                profile?.firstName ?: "",
                profile?.lastName ?: "",
            )
        }
    }

    fun getTitle(): String {
        return when {
            firstName.isNotBlank() && lastName.isNotBlank() -> "$firstName $lastName"
            else -> name
        }
    }
}
