package de.sambalmueslie.openevent.core.logic.notification


import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class NotificationService {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(NotificationService::class.java)
    }

    fun <T> process(event: NotificationEvent<T>) {
        val key = event.key
//        val definition = dbService.findByKey(key) ?: return logger.warn("Cannot find definition by $key")
    }


}
