package de.sambalmueslie.openevent.core.model


import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest

class ParticipantChangeRequest(
    val size: Long,
    val status: ParticipantStatus,
) : BusinessObjectChangeRequest
