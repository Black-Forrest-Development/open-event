package de.sambalmueslie.openevent.user.api

import de.sambalmueslie.openevent.common.crud.BusinessObject

data class User(
    override val id: Long,
    val externalId: String,
    val type: UserType,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val mobile: String,
    val phone: String,
) : BusinessObject<Long>
