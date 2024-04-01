package de.sambalmueslie.openevent.infrastructure.search

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

class OpenSearchClient<I, O> : SearchClient<I, O> {
    override fun delete(key: String) {
        TODO("Not yet implemented")
    }

    override fun deleteAll() {
        TODO("Not yet implemented")
    }

    override fun get(key: String): O? {
        TODO("Not yet implemented")
    }

    override fun save(input: List<I>) {
        TODO("Not yet implemented")
    }

    override fun search(query: String, pageable: Pageable): Page<O> {
        TODO("Not yet implemented")
    }
}