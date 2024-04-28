package de.sambalmueslie.openevent.core.search.api

import java.time.LocalDateTime


data class EventSearchRequest(
    val fullTextSearch: String,
    val from: LocalDateTime?,
    val to: LocalDateTime?,
    val ownEvents: Boolean,
    val participatingEvents: Boolean
): SearchRequest
