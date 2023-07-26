package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObject

data class Account(
    override val id: Long,
    val externalId: String?,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val iconUrl: String,
    val serviceUser: Boolean,
) : BusinessObject<Long> {


    fun getTitle(): String {
        return when {
            firstName.isNotBlank() && lastName.isNotBlank() -> "$firstName $lastName"
            else -> userName
        }
    }
}
