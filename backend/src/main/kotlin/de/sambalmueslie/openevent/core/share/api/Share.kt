package de.sambalmueslie.openevent.core.share.api

import de.sambalmueslie.openevent.common.BusinessObject
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDateTime

@Serdeable
data class Share(
    override val id: String,
    val eventId: Long,
    val enabled: Boolean,

    val created: LocalDateTime,
    val changed: LocalDateTime?
) : BusinessObject<String>
