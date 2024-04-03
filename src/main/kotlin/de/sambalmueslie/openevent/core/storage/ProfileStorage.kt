package de.sambalmueslie.openevent.core.storage

import de.sambalmueslie.openevent.core.Storage
import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.core.model.Profile
import de.sambalmueslie.openevent.core.model.ProfileChangeRequest

interface ProfileStorage : Storage<Long, Profile, ProfileChangeRequest> {
    fun create(request: ProfileChangeRequest, account: Account): Profile
    fun findByAccount(account: Account): Profile?
    fun getForAccounts(accounts: Collection<Account>): List<Profile>

}
