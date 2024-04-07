package de.sambalmueslie.openevent.core.logic.category.db

import de.sambalmueslie.openevent.core.Storage
import de.sambalmueslie.openevent.core.logic.category.api.Category
import de.sambalmueslie.openevent.core.logic.category.api.CategoryChangeRequest

interface CategoryStorage : Storage<Long, Category, CategoryChangeRequest> {
    fun findByName(name: String): Category?
}
