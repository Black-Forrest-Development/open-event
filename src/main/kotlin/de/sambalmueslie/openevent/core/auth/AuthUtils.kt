package de.sambalmueslie.openevent.core.auth

import de.sambalmueslie.openevent.error.InsufficientPermissionsException
import io.micronaut.security.authentication.Authentication


fun <T> Authentication.checkPermission(permission: String, function: () -> T): T {
    if (roles.contains(permission)) return function.invoke()
    val permissions = attributes["permissions"]
    if (permissions != null) {
        val values =
            permissions as? List<*> ?: throw InsufficientPermissionsException("No permission to access resource")
        if (values.contains(permission)) return function.invoke()
    }
    throw InsufficientPermissionsException("No permission to access resource")
}

fun Authentication.getEmail(): String {
    return attributes["email"] as? String ?: ""
}

fun Authentication.getUsername(): String {
    return attributes["preferred_username"] as? String ?: ""
}

fun Authentication.getExternalId(): String {
    return attributes["sub"] as? String ?: ""
}

fun Authentication.getFirstName(): String {
    return attributes["given_name"] as? String ?: ""
}

fun Authentication.getLastName(): String {
    return attributes["family_name"] as? String ?: ""
}

class AuthUtils

