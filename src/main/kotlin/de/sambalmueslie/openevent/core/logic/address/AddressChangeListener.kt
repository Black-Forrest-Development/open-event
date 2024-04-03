package de.sambalmueslie.openevent.core.logic.address

import de.sambalmueslie.openevent.core.BusinessObjectChangeListener
import de.sambalmueslie.openevent.core.model.Address

interface AddressChangeListener : BusinessObjectChangeListener<Long, Address>
