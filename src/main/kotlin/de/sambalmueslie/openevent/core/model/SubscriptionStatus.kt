package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.logic.notification.api.NotificationScheme

data class SubscriptionStatus(
    val unsubscribed: List<NotificationScheme>,
    val subscribed: List<NotificationScheme>,
)
