package de.sambalmueslie.openevent.core.logic.category.api

import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest
import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class CategoryChangeRequest(
    val name: String,
    val iconUrl: String
) : BusinessObjectChangeRequest
