package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObject
import java.time.LocalDateTime

data class Message(
    override val id: Long,
    val subject: String,
    val content: String,
    val timestamp: LocalDateTime,
    val author: Account,
) : BusinessObject<Long>
