package de.sambalmueslie.openevent.infrastructure.cache

import com.github.benmanes.caffeine.cache.LoadingCache
import java.util.*

class KotlinLoadingCache<K : Any, V : Any>(
    private val cache: LoadingCache<K, Optional<V>>
) {
    operator fun get(id: K): V? {
        return cache[id].orElseGet { null }
    }

    fun put(id: K, data: V) {
        val value = Optional.of<V>(data)
        cache.put(id, value)
    }

    fun invalidate(id: K) {
        cache.invalidate(id)
    }

    fun invalidateAll() {
        cache.invalidateAll()
    }


}