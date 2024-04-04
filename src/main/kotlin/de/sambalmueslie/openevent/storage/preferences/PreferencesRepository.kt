package de.sambalmueslie.openevent.storage.preferences


import de.sambalmueslie.openevent.storage.DataObjectRepository
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface PreferencesRepository : DataObjectRepository<Long, PreferencesData> {
    fun findByAccountId(accountId: Long): PreferencesData?
}
