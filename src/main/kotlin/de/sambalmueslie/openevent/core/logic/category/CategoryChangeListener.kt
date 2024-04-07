package de.sambalmueslie.openevent.core.logic.category

import de.sambalmueslie.openevent.core.BusinessObjectChangeListener
import de.sambalmueslie.openevent.core.logic.category.api.Category

interface CategoryChangeListener : BusinessObjectChangeListener<Long, Category>
