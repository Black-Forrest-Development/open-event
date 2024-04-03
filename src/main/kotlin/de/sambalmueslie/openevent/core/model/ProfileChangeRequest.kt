package de.sambalmueslie.openevent.core.model

import de.sambalmueslie.openevent.core.BusinessObjectChangeRequest
import io.micronaut.serde.annotation.Serdeable
import java.time.LocalDate

@Serdeable
data class ProfileChangeRequest(

    val email: String,
    val phone: String,
    val mobile: String,

    val firstName: String,
    val lastName: String,

    val dateOfBirth: LocalDate = LocalDate.MIN,
    val gender: String = "",
    val profilePicture: String = "",
    val website: String = ""

) : BusinessObjectChangeRequest