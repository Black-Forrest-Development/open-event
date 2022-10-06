@file:Suppress("BlockingMethodInNonBlockingContext")

package de.sambalmueslie.openevent.user


import com.github.benmanes.caffeine.cache.Caffeine
import com.github.benmanes.caffeine.cache.LoadingCache
import de.sambalmueslie.openevent.common.crud.BaseCrudService
import de.sambalmueslie.openevent.common.error.InvalidRequestException
import de.sambalmueslie.openevent.common.time.TimeProvider
import de.sambalmueslie.openevent.common.util.findByIdOrNull
import de.sambalmueslie.openevent.user.api.User
import de.sambalmueslie.openevent.user.api.UserChangeRequest
import de.sambalmueslie.openevent.user.db.UserData
import de.sambalmueslie.openevent.user.db.UserRepository
import de.sambalmueslie.openevent.user.idp.update.IdpUserUpdater
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono
import java.time.Duration

@Singleton
class UserService(
    private val repository: UserRepository,
    private val timeProvider: TimeProvider
) : BaseCrudService<Long, User, UserChangeRequest>(logger) {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UserService::class.java)
    }

    override fun get(id: Long): Mono<User> {
        return Mono.justOrEmpty(repository.findByIdOrNull(id))
    }

    override fun getAll(pageable: Pageable): Mono<Page<User>> {
        return Mono.just(repository.findAll(pageable).map { it.convert() })
    }


    override fun create(request: UserChangeRequest): Mono<User> {
        isValid(request)
        val existing = repository.findByEmail(request.email)
        if (existing.isNotEmpty()) throw InvalidRequestException("User email is already existing ${existing.size} times")

        val data = repository.save(UserData.create(request, timeProvider.now()))
        val result = repository.findByIdOrNull(data.id) ?: return Mono.empty()
        notifyCreated(result)
        return Mono.just(result)
    }

    override fun update(id: Long, request: UserChangeRequest): Mono<User> {
        val data = repository.findByIdOrNull(id) ?: return create(request)
        isValid(request)
        repository.update(data.update(request, timeProvider.now()))
        val result = repository.findByIdOrNull(data.id) ?: return Mono.empty()
        notifyUpdated(result)
        return Mono.just(result)
    }

    override fun delete(id: Long): Mono<Long> {
        val data = repository.findByIdOrNull(id) ?: return Mono.empty()
        repository.delete(data)
        notifyDeleted(data)
        return Mono.just(id)
    }

    private fun isValid(request: UserChangeRequest) {
        if (request.email.isBlank()) throw InvalidRequestException("User email could not be blank")
        if (request.firstName.isBlank()) throw InvalidRequestException("User first name could not be blank")
        if (request.lastName.isBlank()) throw InvalidRequestException("User last name could not be blank")
    }

}
