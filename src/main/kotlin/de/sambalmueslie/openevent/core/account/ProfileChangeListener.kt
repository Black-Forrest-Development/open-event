package de.sambalmueslie.openevent.core.account

import de.sambalmueslie.openevent.common.BusinessObjectChangeListener
import de.sambalmueslie.openevent.core.logic.account.api.Profile

interface ProfileChangeListener : BusinessObjectChangeListener<Long, Profile>
