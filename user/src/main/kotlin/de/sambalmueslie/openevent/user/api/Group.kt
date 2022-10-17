package de.sambalmueslie.openevent.user.api

import de.sambalmueslie.openevent.common.crud.BusinessObject

data class Group(
    override val id: Long,
    val name: String
) : BusinessObject<Long>
