package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObject
import java.time.LocalDateTime

data class Announcement(
    override val id: Long,
    val subject: String,
    val content: String,
    val author: Account,
    val timestamp: LocalDateTime
) : BusinessObject<Long>
