package de.sambalmueslie.openevent.core.logic.address

import de.sambalmueslie.openevent.core.BusinessObjectChangeListener
import de.sambalmueslie.openevent.core.logic.address.api.Address

interface AddressChangeListener : BusinessObjectChangeListener<Long, Address>
