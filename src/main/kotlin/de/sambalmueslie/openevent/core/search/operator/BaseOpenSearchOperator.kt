package de.sambalmueslie.openevent.core.search.operator


import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import com.jillesvangurp.ktsearch.*
import com.jillesvangurp.searchdsls.mappingdsl.FieldMappings
import de.sambalmueslie.openevent.common.PageSequence
import de.sambalmueslie.openevent.core.search.api.SearchRequest
import de.sambalmueslie.openevent.core.search.api.SearchResponse
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

    protected val mapper = ObjectMapper()
        .registerKotlinModule()
        .registerModule(JavaTimeModule())
        .setSerializationInclusion(JsonInclude.Include.ALWAYS)
        .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)

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
        runBlocking { indexDocument(data.first, data.second) }
    }

    protected fun updateDocument(data: Pair<String, String>) {
        runBlocking { indexDocument(data.first, data.second) }
    }

    private suspend fun indexDocument(id: String, value: String) {
        val duration = measureTimeMillis {
            val existing = try {
                client.getDocument(target = name, id = id).found
            } catch (e: Exception) {
                false
            }
            val type = if (existing) OperationType.Index else OperationType.Create

            client.indexDocument(
                target = name,
                serializedJson = value,
                id = id,
                opType = type
            )
        }
        logger.info("[$name] index document within $duration ms.")
    }

    private suspend fun indexDocument(id: String, value: String, type: OperationType) {
        val duration = measureTimeMillis {
            client.indexDocument(
                target = name,
                serializedJson = value,
                id = id,
                opType = type
            )
        }
        logger.info("[$name] index document within $duration ms.")
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