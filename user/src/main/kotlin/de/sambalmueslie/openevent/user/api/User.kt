package de.sambalmueslie.openevent.user.api

import de.sambalmueslie.openevent.common.crud.BusinessObject

interface User : BusinessObject<Long> {
    override val id: Long
    val externalId: String
    val name: String
    val email: String
}
