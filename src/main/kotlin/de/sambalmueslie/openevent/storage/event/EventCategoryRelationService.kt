package de.sambalmueslie.openevent.storage.event


import de.sambalmueslie.openevent.core.model.Category
import de.sambalmueslie.openevent.core.model.Event
import de.sambalmueslie.openevent.storage.category.CategoryStorageService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class EventCategoryRelationService(
    private val repository: CategoryRelationRepository,
    private val service: CategoryStorageService
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EventCategoryRelationService::class.java)
    }

    fun assign(event: Event, category: Category) {
        if (repository.existsByCategoryIdAndEventId(category.id, event.id)) return

        val relation = EventCategoryRelation(category.id, event.id)
        repository.save(relation)
    }

    fun revoke(event: Event, category: Category) {
        repository.deleteByCategoryIdAndEventId(category.id, event.id)
    }

    fun get(event: Event, pageable: Pageable): Page<Category> {
        val relations = repository.findByEventId(event.id, pageable)
        val categoryIds = relations.content.map { it.categoryId }.toSet()
        val result = service.findByIds(categoryIds)
        return Page.of(result, relations.pageable, relations.totalSize)
    }

}
