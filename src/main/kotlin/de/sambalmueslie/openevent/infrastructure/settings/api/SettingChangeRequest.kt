package de.sambalmueslie.openevent.infrastructure.settings.api

import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest
import de.sambalmueslie.openevent.core.model.ValueType

data class SettingChangeRequest(
    val key: String,
    val value: Any,
    val type: ValueType
) : BusinessObjectChangeRequest
