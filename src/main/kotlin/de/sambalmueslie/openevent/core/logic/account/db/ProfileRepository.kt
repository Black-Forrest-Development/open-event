package de.sambalmueslie.openevent.core.logic.account.db


import de.sambalmueslie.openevent.storage.DataObjectRepository
import io.micronaut.data.annotation.Repository
import io.micronaut.data.jdbc.annotation.JdbcRepository
import io.micronaut.data.model.query.builder.sql.Dialect

@Repository
@JdbcRepository(dialect = Dialect.POSTGRES)
interface ProfileRepository : DataObjectRepository<Long, ProfileData> {
    fun findByAccountId(accountId: Long): ProfileData?
    fun findByAccountIdIn(accountIds: Set<Long>): List<ProfileData>
}
