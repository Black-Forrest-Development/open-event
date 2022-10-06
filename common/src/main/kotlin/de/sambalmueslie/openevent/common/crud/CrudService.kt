package de.sambalmueslie.openevent.common.crud

import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import reactor.core.publisher.Mono


interface CrudService<T, O : BusinessObject<T>, R : BusinessObjectChangeRequest> {
    fun get(id: T): Mono<O>
    fun getAll(pageable: Pageable): Mono<Page<O>>
    fun create(request: R): Mono<O>
    fun update(id: T, request: R): Mono<O>
    fun delete(id: T): Mono<Long>

    fun register(listener: BusinessObjectChangeListener<T, O>)
    fun unregister(listener: BusinessObjectChangeListener<T, O>)
}
