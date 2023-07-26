package de.sambalmueslie.openevent.guild.db

import de.sambalmueslie.openevent.common.crud.DataObject
import de.sambalmueslie.openevent.guild.permission.api.GuildPermission
import jakarta.persistence.*

@Suppress("JpaObjectClassSignatureInspection")
@Entity(name = "GuildPermission")
@Table(name = "guild_permission")
data class GuildPermissionData(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    var id: Long,
    @Column()
    var name: String,
    @Column()
    var description: String
) : DataObject<GuildPermission> {
    override fun convert() = GuildPermission(id, name, description)

}
