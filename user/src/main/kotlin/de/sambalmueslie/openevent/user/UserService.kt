package de.sambalmueslie.openevent.user


import de.sambalmueslie.openevent.common.crud.CrudService
import de.sambalmueslie.openevent.user.api.User
import de.sambalmueslie.openevent.user.api.UserChangeRequest
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import io.micronaut.security.authentication.Authentication
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import reactor.core.publisher.Mono

@Singleton
class UserService : CrudService<Long, User, UserChangeRequest> {
    companion object {
        private val logger: Logger = LoggerFactory.getLogger(UserService::class.java)
    }

	override fun get(auth: Authentication, id: Long): Mono<User> {
		TODO("Not yet implemented")
	}

	override fun getAll(auth: Authentication, pageable: Pageable): Mono<Page<User>> {
		TODO("Not yet implemented")
	}

	override fun delete(auth: Authentication, id: Long): Mono<Long> {
		TODO("Not yet implemented")
	}

	override fun update(auth: Authentication, id: Long, request: UserChangeRequest): Mono<User> {
		TODO("Not yet implemented")
	}

	override fun create(auth: Authentication, request: UserChangeRequest): Mono<User> {
		TODO("Not yet implemented")
	}


}
