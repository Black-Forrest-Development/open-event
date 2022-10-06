package de.sambalmueslie.openevent.user.db


import de.sambalmueslie.openevent.common.crud.DataObject
import de.sambalmueslie.openevent.user.api.User
import de.sambalmueslie.openevent.user.api.UserChangeRequest
import jakarta.persistence.*
import java.time.LocalDateTime

@Suppress("JpaObjectClassSignatureInspection")
@Entity(name = "User")
@Table(name = "_user")
data class UserData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    override var id: Long,
    @Column
    override var externalId: String,
    @Column
    override var userName: String,
    @Column
    override var email: String,
    @Column
    override var firstName: String,
    @Column
    override var lastName: String,
    @Column
    var created: LocalDateTime,
    @Column
    var updated: LocalDateTime?,
) : User, DataObject<User> {

    companion object {
        fun create(request: UserChangeRequest, timestamp: LocalDateTime): UserData {
            return UserData(0, request.externalId, request.userName, request.email, request.firstName, request.lastName, timestamp, null)
        }
    }

    override fun convert(): User {
        return this
    }

    fun update(request: UserChangeRequest, timestamp: LocalDateTime): UserData {
        externalId = request.externalId
        userName = request.userName
        email = request.email
        firstName = request.firstName
        lastName = request.lastName
        updated = timestamp
        return this
    }
}
