package de.sambalmueslie.openevent.core.logic

import de.sambalmueslie.openevent.core.model.Category
import de.sambalmueslie.openevent.core.model.CategoryChangeRequest
import io.micronaut.data.model.Pageable
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

@MicronautTest
class CategoryCrudServiceTest {

    @Inject
    lateinit var service: CategoryCrudService

    @Test
    fun categoryCrud() {
        val request = CategoryChangeRequest("name", "icon-url")
        var category = service.create(request)

        assertEquals(request.name, category.name)
        assertEquals(request.iconUrl, category.iconUrl)
        assertTrue(category.id > 0)

        assertEquals(category, service.get(category.id))
        assertEquals(category, service.findByName(request.name))
        assertEquals(listOf(category), service.getAll(Pageable.from(0)).content)

        val update = CategoryChangeRequest("name-update", "icon-url-update")
        category = service.update(category.id, update)

        assertEquals(update.name, category.name)
        assertEquals(update.iconUrl, category.iconUrl)

        service.delete(category.id)
        assertEquals(emptyList<Category>(), service.getAll(Pageable.from(0)).content)

    }

}
