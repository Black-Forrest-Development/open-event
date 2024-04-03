package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObject
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class Preferences(
    override val id: Long,
    val notifyOnEventChanges: Boolean
) : BusinessObject<Long>
