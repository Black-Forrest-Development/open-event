package de.sambalmueslie.openevent.api

import de.sambalmueslie.openevent.common.PatchRequest
import de.sambalmueslie.openevent.core.share.api.Share
import de.sambalmueslie.openevent.core.share.api.ShareChangeRequest
import de.sambalmueslie.openevent.core.share.api.SharedInfo
import io.micronaut.security.authentication.Authentication

interface ShareAPI : CrudAPI<String, Share, ShareChangeRequest> {
    companion object {
        const val PERMISSION_READ = "openevent.share.read"
        const val PERMISSION_WRITE = "openevent.share.write"
        const val PERMISSION_ADMIN = "openevent.share.admin"
    }

    fun setPublished(auth: Authentication, id: String, value: PatchRequest<Boolean>): Share?
    fun getInfo(auth: Authentication?, id: String): SharedInfo?
}