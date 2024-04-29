package de.sambalmueslie.openevent.core.search.operator

import com.jillesvangurp.ktsearch.*
import com.jillesvangurp.searchdsls.mappingdsl.FieldMappings
import de.sambalmueslie.openevent.common.PageSequence
import de.sambalmueslie.openevent.core.search.api.SearchRequest
import de.sambalmueslie.openevent.core.search.api.SearchResponse
import de.sambalmueslie.openevent.infrastructure.search.OpenSearchService
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.scheduling.annotation.Async
import kotlinx.coroutines.runBlocking
import org.slf4j.Logger
import kotlin.system.measureTimeMillis
import kotlin.time.Duration.Companion.seconds

abstract class BaseOpenSearchOperator<T, R : SearchRequest, S : SearchResponse<T>>(
    openSearch: OpenSearchService,
    protected val name: String,
    private val logger: Logger
) : SearchOperator<T, R, S> {

    protected val client = openSearch.getClient()

    @Async
    override fun setup() {
        runBlocking {
            var duration = measureTimeMillis { createIndex() }
            logger.info("[$name] Create index within $duration ms.")
            duration = measureTimeMillis { initialLoad() }
            logger.info("[$name] Initial load within $duration ms.")
        }
    }

    private suspend fun createIndex() {
        val exists = client.exists(name)
        if (exists) client.deleteIndex(name)

        client.createIndex(name) {
            settings {
                replicas = 0
                shards = 3
                refreshInterval = 1.seconds
            }
            mappings(dynamicEnabled = false, createMappings())
        }

        // micronaut opensearch
//        val exists = client.indices().exists(ExistsRequest.Builder().index(name).build()).value()
//        if (exists) client.indices().delete(DeleteIndexRequest.Builder().index(name).build())
//
//        val settings = IndexSettings.Builder()
//            .numberOfReplicas("0")
//            .numberOfShards("3")
//            .build()
//
//        val request: CreateIndexRequest = CreateIndexRequest.Builder()
//            .index(name)
//            .settings(settings)
//            .mappings(createMappings())
//            .build()
//
//        client.indices().create(request)
    }

    abstract fun createMappings(): FieldMappings.() -> Unit


    private suspend fun initialLoad() {
        val data = PageSequence { initialLoadPage(it) }

        data.forEach { page ->
            client.bulk(target = name) {
                page.forEach { (id, value) -> index(value, id = id) }
            }
        }
    }

    protected fun createDocument(data: Pair<String, String>) {
        indexDocument(data)
    }

    protected fun updateDocument(data: Pair<String, String>) {
        indexDocument(data)
    }

    private fun indexDocument(data: Pair<String, String>) {
        runBlocking {
            val id = data.first
            val existing = client.getDocument(target = name, id = id)
            val type = if (existing.found) OperationType.Index else OperationType.Create
            val duration = measureTimeMillis {
                val input = data.second
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

    protected fun deleteDocument(id: String) {
        runBlocking {
            val duration = measureTimeMillis {
                client.deleteDocument(name, id)
            }
            logger.info("[$name] delete document within $duration ms.")
        }
    }

    protected abstract fun initialLoadPage(pageable: Pageable): Page<Pair<String, String>>


}