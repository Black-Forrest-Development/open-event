package de.sambalmueslie.openevent.core.logic.account

import de.sambalmueslie.openevent.core.Storage
import de.sambalmueslie.openevent.core.logic.account.api.Account
import de.sambalmueslie.openevent.core.logic.account.api.Profile
import de.sambalmueslie.openevent.core.logic.account.api.ProfileChangeRequest

interface ProfileStorage : Storage<Long, Profile, ProfileChangeRequest> {
    fun create(request: ProfileChangeRequest, account: Account): Profile
    fun findByAccount(account: Account): Profile?
    fun getForAccounts(accounts: Collection<Account>): List<Profile>

}
