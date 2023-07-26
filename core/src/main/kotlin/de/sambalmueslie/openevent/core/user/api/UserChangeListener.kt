package de.sambalmueslie.openevent.core.user.api

import de.sambalmueslie.openevent.common.crud.BusinessObjectChangeListener

interface UserChangeListener : BusinessObjectChangeListener<Long, User>
