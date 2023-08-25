package de.sambalmueslie.openevent.storage.notification

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.GenericRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface TypeSchemeRelationRepository : GenericRepository<TypeSchemeRelation, Long> {

    fun findBySchemeId(schemaId: Long): List<TypeSchemeRelation>

    fun saveAll(data: List<TypeSchemeRelation>): List<TypeSchemeRelation>

    fun deleteBySchemeId(schemaId: Long)
    fun deleteByTypeId(it: Long)
}
