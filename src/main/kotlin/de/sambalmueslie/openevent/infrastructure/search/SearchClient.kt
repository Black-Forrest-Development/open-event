package de.sambalmueslie.openevent.infrastructure.search

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

interface SearchClient<I, O> {
    fun delete(key: String)
    fun deleteAll()
    fun get(key: String): O?
    fun save(input: List<I>)
    fun search(query: String, pageable: Pageable): Page<O>
}
