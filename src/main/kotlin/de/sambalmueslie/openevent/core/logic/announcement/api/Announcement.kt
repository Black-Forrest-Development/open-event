package de.sambalmueslie.openevent.core.logic.announcement.api

import de.sambalmueslie.openevent.core.BusinessObject
import de.sambalmueslie.openevent.core.logic.account.api.Account
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime
@Serdeable
data class Announcement(
    override val id: Long,
    val subject: String,
    val content: String,
    val author: Account,
    val timestamp: LocalDateTime
) : BusinessObject<Long>
