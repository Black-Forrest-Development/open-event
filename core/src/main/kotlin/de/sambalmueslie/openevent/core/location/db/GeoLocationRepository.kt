package de.sambalmueslie.openevent.core.location.db

import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect
import io.micronaut.data.repository.PageableRepository

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface GeoLocationRepository : PageableRepository<GeoLocationData, Long> {
    fun findByLocationId(locationId: Long): GeoLocationData?
    fun deleteByLocationId(locationId: Long)
}
