package de.sambalmueslie.openevent.core.share.api

import io.micronaut.serde.annotation.Serdeable

@Serdeable
data class ShareInfo(
    val share: Share,
    val url: String,
)
