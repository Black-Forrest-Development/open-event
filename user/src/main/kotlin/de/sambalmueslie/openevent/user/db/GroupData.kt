package de.sambalmueslie.openevent.user.db

import de.sambalmueslie.openevent.common.crud.DataObject
import de.sambalmueslie.openevent.user.api.Group
import de.sambalmueslie.openevent.user.api.GroupChangeRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Suppress("JpaObjectClassSignatureInspection")
@Entity(name = "Group")
@Table(name = "_group")
data class GroupData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long,
    @Column
    var name: String,
    @Column
    var created: LocalDateTime,
    @Column
    var updated: LocalDateTime?,
) : DataObject<Group> {

    companion object {
        fun create(request: GroupChangeRequest, timestamp: LocalDateTime): GroupData {
            return GroupData(0, request.name, timestamp, null)
        }
    }

    override fun convert() = Group(id, name)

    fun update(request: GroupChangeRequest, timestamp: LocalDateTime): GroupData {
        name = request.name
        updated = timestamp
        return this
    }
}

