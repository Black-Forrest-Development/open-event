package de.sambalmueslie.openevent.infrastructure.search


import com.jillesvangurp.ktsearch.*
import com.jillesvangurp.searchdsls.mappingdsl.FieldMappings
import com.jillesvangurp.searchdsls.querydsl.ESQuery
import de.sambalmueslie.openevent.common.BusinessObject
import de.sambalmueslie.openevent.common.CrudService
import de.sambalmueslie.openevent.common.PageableSequence
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.scheduling.annotation.Async
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.seconds

abstract class BaseSearchService<T, O : BusinessObject<T>>(
    private val service: CrudService<T, O, *, *>,
    searchService: SearchService,
    private val name: String,
    private val logger: Logger
) {

    private val client = searchService.getClient()

    protected fun handleCreated(obj: O) {
        updateDocument(obj)
    }

    protected fun handleChanged(obj: O) {
        updateDocument(obj)
    }

    private fun updateDocument(obj: O) {
        runBlocking {
            val id = obj.id.toString()
            val duration = measureTimeMillis {
                val existing = client.getDocument(target = name, id = id)
                val type = if (existing.found) OperationType.Index else OperationType.Create
                val input = convert(obj)
                client.indexDocument(
                    target = name,
                    serializedJson = input,
                    id = id,
                    opType = type
                )
            }
            logger.info("[$name] index document within $duration ms.")
        }
    }

    protected fun handleRemoved(obj: O) {
        runBlocking {
            val duration = measureTimeMillis {
                client.deleteDocument(name, obj.id.toString())
            }
            logger.info("[$name] delete document within $duration ms.")
        }
    }


    @Async
    open fun setup() {
        runBlocking {
            var duration = measureTimeMillis { createIndex() }
            logger.info("[$name] Create index within $duration ms.")
            duration = measureTimeMillis { initialLoad() }
            logger.info("[$name] Initial load within $duration ms.")
        }

    }

    private suspend fun initialLoad() {
        val sequence = PageableSequence { service.getAll(it) }
        client.bulk(
            target = name
        ) {
            sequence.forEach { obj ->
                val c = convert(obj)
                index(c, id = obj.id.toString())
            }
        }
    }

    private suspend fun createIndex() {
        val exists = client.exists(name)
        if (exists) client.deleteIndex(name)

        client.createIndex(name) {
            settings {
                replicas = 0
                shards = 3
                refreshInterval = 10.seconds
            }
            mappings(dynamicEnabled = false, createMappings())
        }
    }

    abstract fun convert(obj: O): String

    abstract fun createMappings(): FieldMappings.() -> Unit

    fun search(q: String, pageable: Pageable): Page<O> {
        val result = runBlocking {
            client.search(name) {
                from = pageable.offset.toInt()
                resultSize = pageable.size
                trackTotalHits = "true"
                query = buildQuery(q)
            }
        }
        val ids = result.ids.mapNotNull { convertId(it) }.toSet()
        val objs = service.getByIds(ids)
        return Page.of(objs, pageable, result.total)
    }

    abstract fun buildQuery(q: String): ESQuery


    abstract fun convertId(id: String): T?

}
