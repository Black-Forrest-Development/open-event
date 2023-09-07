package de.sambalmueslie.openevent.api

import io.micronaut.http.server.types.files.SystemFile
import io.micronaut.security.authentication.Authentication

interface ExportAPI {

    companion object {
        const val PERMISSION_EXPORT = "openevent.export"
    }

    fun exportEventsExcel(auth: Authentication): SystemFile?
    fun exportEventExcel(auth: Authentication, eventId: Long): SystemFile?

    fun exportEventsPdf(auth: Authentication): SystemFile?
    fun exportEventPdf(auth: Authentication, eventId: Long): SystemFile?
}
