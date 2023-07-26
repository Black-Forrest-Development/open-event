package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObject

data class Category(
    override val id: Long,
    val name: String,
    val iconUrl: String
) : BusinessObject<Long>
