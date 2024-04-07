package de.sambalmueslie.openevent.core.logic.event.api


data class EventStats(
    val event: Event,
    val isFull: Boolean,
    val isEmpty: Boolean,
    val participantsSize: Long,
    val participantsAmount: Long,
    val waitingListSize: Long,
    val waitingListAmount: Long
)
