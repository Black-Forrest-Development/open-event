package de.sambalmueslie.openevent.user


import de.sambalmueslie.openevent.user.api.Group
import de.sambalmueslie.openevent.user.api.User
import de.sambalmueslie.openevent.user.db.GroupUserRelation
import de.sambalmueslie.openevent.user.db.GroupUserRelationRepository
import io.micronaut.data.model.Page
import io.micronaut.data.model.Pageable
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class RelationService(
    private val repository: GroupUserRelationRepository,
    private val userService: UserService,
    private val groupService: GroupService
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(RelationService::class.java)
    }

    fun getGroupMember(groupId: Long, pageable: Pageable = Pageable.from(0)): Page<User> {
        return repository.findByGroupId(groupId, pageable).map { userService.get(it.userId) }
    }

    fun getUserGroups(userId: Long, pageable: Pageable = Pageable.from(0)): Page<Group> {
        return repository.findByUserId(userId, pageable).map { groupService.get(it.groupId) }
    }

    fun assignUserToGroup(userId: Long, groupId: Long): Page<Group> {
        if (!repository.existsByGroupIdAndUserId(groupId, userId)) {
            repository.save(GroupUserRelation(groupId, userId))
        }
        return getUserGroups(userId)
    }

    fun revokeUserFromGroup(userId: Long, groupId: Long): Page<Group> {
        repository.deleteByGroupIdAndUserId(groupId, userId)
        return getUserGroups(userId)
    }
}
