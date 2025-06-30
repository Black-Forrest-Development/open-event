package de.sambalmueslie.openevent.infrastructure.cache


import com.github.benmanes.caffeine.cache.LoadingCache
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.util.*
import kotlin.reflect.KClass

@Singleton
class CacheService {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(CacheService::class.java)
    }

    private val caches = mutableMapOf<String, LoadingCache<*, *>>()

    fun <T : Any, O : Any> registerNotNullable(key: String, builder: () -> LoadingCache<T, O>): LoadingCache<T, O> {
        return register(key, builder)
    }

    fun <T : Any, O : Any> registerNullable(key: String, builder: () -> LoadingCache<T, Optional<O>>): KotlinLoadingCache<T, O> {
        val cache = register(key, builder)
        return KotlinLoadingCache(cache)
    }

    fun <T : Any, O : Any> register(type: KClass<O>, builder: () -> LoadingCache<T, Optional<O>>): KotlinLoadingCache<T, O> {
        val cache = register(type.java.canonicalName, builder)
        return KotlinLoadingCache(cache)
    }

    private fun <T : Any, O : Any> register(key: String, builder: () -> LoadingCache<T, O>): LoadingCache<T, O> {
        logger.info("Register cache for $key")
        val cache = builder.invoke()
        caches[key] = cache
        return cache
    }

    fun get(key: String): CacheInfo? {
        val cache = caches[key] ?: return null
        return convert(key, cache)
    }

    fun getAll(): List<CacheInfo> {
        return caches.entries.map { convert(it.key, it.value) }
    }

    private fun convert(key: String, cache: LoadingCache<*, *>): CacheInfo {
        val name = key.substringAfterLast('.')
        val stats = cache.stats()
        return CacheInfo(
            key,
            name,
            stats.hitCount(),
            stats.missCount(),
            stats.loadSuccessCount(),
            stats.loadFailureCount(),
            stats.totalLoadTime(),
            stats.evictionCount(),
            stats.evictionWeight()
        )
    }

    fun reset(key: String): CacheInfo? {
        val cache = caches[key] ?: return null
        cache.invalidateAll()
        return convert(key, cache)
    }

    fun resetAll(): List<CacheInfo> {
        return caches.entries.map {
            it.value.invalidateAll()
            convert(it.key, it.value)
        }
    }


}
