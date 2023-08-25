package de.sambalmueslie.openevent.core.logic.registration

import de.sambalmueslie.openevent.core.BusinessObjectChangeListener
import de.sambalmueslie.openevent.core.model.Registration

interface RegistrationChangeListener : BusinessObjectChangeListener<Long, Registration>
