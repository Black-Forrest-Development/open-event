package de.sambalmueslie.openevent.core.search.operator

import com.jillesvangurp.searchdsls.mappingdsl.FieldMappings
import de.sambalmueslie.openevent.core.category.api.Category
import kotlinx.serialization.Serializable

@Serializable
data class CategorySearchEntryData(
    var id: String,
    var name: String,
) {
    companion object {
        fun createMappings(): FieldMappings.() -> Unit {
            return {
                number<Long>("id")
                text("name")
            }
        }

        fun create(obj: Category): CategorySearchEntryData {
            return CategorySearchEntryData(
                obj.id.toString(),
                obj.name
            )
        }
    }
}
