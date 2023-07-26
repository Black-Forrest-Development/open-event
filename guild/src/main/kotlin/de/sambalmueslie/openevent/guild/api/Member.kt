package de.sambalmueslie.openevent.guild.api

import de.sambalmueslie.openevent.common.crud.BusinessObject

data class Member(
    override val id: Long,
): BusinessObject<Long>
