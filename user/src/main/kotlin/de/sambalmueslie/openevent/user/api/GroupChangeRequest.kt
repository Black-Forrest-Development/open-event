package de.sambalmueslie.openevent.user.api

import de.sambalmueslie.openevent.common.crud.BusinessObjectChangeRequest

data class GroupChangeRequest(
    val name: String
) : BusinessObjectChangeRequest
