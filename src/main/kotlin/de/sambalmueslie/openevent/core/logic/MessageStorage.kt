package de.sambalmueslie.openevent.core.logic

import de.sambalmueslie.openevent.core.Storage
import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.core.model.Message
import de.sambalmueslie.openevent.core.model.MessageChangeRequest

interface MessageStorage : Storage<Long, Message, MessageChangeRequest> {
    fun create(request: MessageChangeRequest, author: Account): Message
}
