package de.sambalmueslie.openevent.core.logic.account.db

import de.sambalmueslie.openevent.core.logic.account.api.Account
import de.sambalmueslie.openevent.core.logic.account.api.Profile
import de.sambalmueslie.openevent.core.logic.account.api.ProfileChangeRequest
import de.sambalmueslie.openevent.storage.SimpleDataObject
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDate
import java.time.LocalDateTime

@Entity(name = "Profile")
@Table(name = "profile")
data class ProfileData(
    @Id var id: Long,

    @Column var email: String?,
    @Column var phone: String?,
    @Column var mobile: String?,

    @Column var firstName: String,
    @Column var lastName: String,

    @Column var dateOfBirth: LocalDate?,
    @Column var gender: String?,
    @Column var profilePicture: String?,
    @Column var website: String?,

    @Column var created: LocalDateTime = LocalDateTime.now(),
    @Column var updated: LocalDateTime? = null
) : SimpleDataObject<Profile> {

    companion object {
        fun create(
            account: Account,
            request: ProfileChangeRequest,
            timestamp: LocalDateTime
        ): ProfileData {
            return ProfileData(
                account.id,
                request.email,
                request.phone,
                request.mobile,
                request.firstName,
                request.lastName,
                request.dateOfBirth,
                request.gender,
                request.profilePicture,
                request.website,
                timestamp
            )
        }
    }

    override fun convert(): Profile {
        return Profile(id, email, phone, mobile, firstName, lastName, dateOfBirth, gender, profilePicture, website)
    }

    fun update(request: ProfileChangeRequest, timestamp: LocalDateTime): ProfileData {
        email = request.email
        phone = request.phone
        mobile = request.mobile
        firstName = request.firstName
        lastName = request.lastName
        dateOfBirth = request.dateOfBirth
        gender = request.gender
        profilePicture = request.profilePicture
        website = request.website
        updated = timestamp
        return this
    }
}
