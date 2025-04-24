package de.sambalmueslie.openevent.core.issue.db

import de.sambalmueslie.openevent.common.Storage
import de.sambalmueslie.openevent.core.account.api.Account
import de.sambalmueslie.openevent.core.issue.api.Issue
import de.sambalmueslie.openevent.core.issue.api.IssueChangeRequest
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable

interface IssueStorage : Storage<Long, Issue, IssueChangeRequest> {
    fun create(request: IssueChangeRequest, account: Account, clientIp: String, userAgent: String): Issue
    fun findByAccount(account: Account, pageable: Pageable): Page<Issue>
    fun getData(id: Long): IssueData?
}
