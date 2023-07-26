package de.sambalmueslie.openevent.guild.db

import de.sambalmueslie.openevent.common.crud.DataObject
import de.sambalmueslie.openevent.guild.permission.api.GuildPermissionScheme
import jakarta.persistence.*

@Suppress("JpaObjectClassSignatureInspection")
@Entity(name = "GuildPermissionScheme")
@Table(name = "guild_permission_scheme")
data class GuildPermissionSchemeData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long,
    @Column()
    var name: String,
    @Column()
    var description: String
) : DataObject<GuildPermissionScheme> {
    override fun convert() = GuildPermissionScheme(id, name, description)

}
