package de.sambalmueslie.openevent.event


import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import io.micronaut.context.event.BeanCreatedEvent
import io.micronaut.context.event.BeanCreatedEventListener
import io.micronaut.runtime.Micronaut
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class EventApplication {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EventApplication::class.java)

        @JvmStatic
        fun main(args: Array<String>) {
            Micronaut.build()
                .packages("de.sambalmueslie.openevent.event")
                .mainClass(EventApplication::class.java)
                .start()
        }
    }

    @Singleton
    internal class ObjectMapperBeanEventListener : BeanCreatedEventListener<ObjectMapper> {
        override fun onCreated(event: BeanCreatedEvent<ObjectMapper>): ObjectMapper {
            val mapper: ObjectMapper = event.getBean()
            mapper.registerModule(JavaTimeModule())
            mapper.registerKotlinModule()
            mapper.enable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            mapper.enable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
            return mapper
        }
    }
}
