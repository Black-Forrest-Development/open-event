package de.sambalmueslie.openevent.config

import io.micronaut.context.annotation.ConfigurationProperties
import jakarta.validation.constraints.NotBlank
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@ConfigurationProperties("app")
class AppConfig {


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(AppConfig::class.java)
    }


    @NotBlank
    var searchEngine: String = "opensearch"
        set(value) {
            logger.info("Set search engine from '$field' to '$value'")
            field = value
        }

    @NotBlank
    var externalParticipantExpires: String = "en"
        set(value) {
            logger.info("Set external participant expires from '$field' to '$value'")
            field = value
        }

    var maxConfirmationTrials: Int = 5
        set(value) {
            logger.info("Set max confirmation trials from '$field' to '$value'")
            field = value
        }

    @NotBlank
    var confirmationBaseUrl: String = "http://localhost:4202"
        set(value) {
            logger.info("Set confirmation baseUrl from '$field' to '$value'")
            field = value
        }
}