package de.sambalmueslie.openevent.core.search.operator

import com.jillesvangurp.searchdsls.mappingdsl.FieldMappings
import com.jillesvangurp.searchdsls.querydsl.*
import de.sambalmueslie.openevent.core.account.api.Account
import de.sambalmueslie.openevent.core.account.api.AccountInfo
import de.sambalmueslie.openevent.core.event.api.EventInfo
import de.sambalmueslie.openevent.core.search.api.EventSearchEntry
import de.sambalmueslie.openevent.core.search.api.EventSearchRequest
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.jsoup.Jsoup
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializer(forClass = LocalDateTime::class)
object DateSerializer : KSerializer<LocalDateTime> {
    private val formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME

    override fun serialize(encoder: Encoder, value: LocalDateTime) {
        encoder.encodeString(value.format(formatter))
    }

    override fun deserialize(decoder: Decoder): LocalDateTime {
        return LocalDateTime.parse(decoder.decodeString(), formatter)
    }
}

@Serializable
data class EventSearchEntryData(
    var id: String,
    @Serializable(with = DateSerializer::class) var start: LocalDateTime,
    @Serializable(with = DateSerializer::class) var finish: LocalDateTime,
    var title: String,
    var shortText: String,
    var longText: String,
    var published: Boolean,
    var owner: Long,

    var hasLocation: Boolean,
    var street: String?,
    var streetNumber: String?,
    var zip: String?,
    var city: String?,
    var country: String?,
    var lat: Double?,
    var lon: Double?,

    var hasSpaceLeft: Boolean,
    var maxGuestAmount: Int,
    var amountAccepted: Int,
    var amountOnWaitingList: Int,
    var remainingSpace: Int,
    var participant: Set<Long>,
    var categories: Set<String>

) {

    companion object {

        fun create(info: EventInfo): EventSearchEntryData {
            val e = info.event
            val l = info.location
            val r = info.registration
            val p = r?.participants ?: emptyList()
            val c = info.categories

            val (waitingList, accepted) = p.partition { it.waitingList }
            val maxGuestAmount = r?.registration?.maxGuestAmount ?: 0
            val amountAccepted = accepted.sumOf { it.size }.toInt()
            val amountOnWaitingList = waitingList.sumOf { it.size }.toInt()
            val remainingSpace = maxGuestAmount - amountAccepted
            val hasSpaceLeft = remainingSpace > 0

            return EventSearchEntryData(
                e.id.toString(),
                e.start,
                e.finish,
                e.title,
                Jsoup.parse(e.shortText).text(),
                Jsoup.parse(e.longText).text(),
                e.published,
                e.owner.id,

                l != null,
                l?.street,
                l?.streetNumber,
                l?.zip,
                l?.city,
                l?.country,
                l?.lat,
                l?.lon,

                hasSpaceLeft,
                maxGuestAmount,
                amountAccepted,
                amountOnWaitingList,
                remainingSpace,

                p.map { it.id }.toSet(),
                c.map { it.name }.toSet()
            )
        }


        fun createMappings(): FieldMappings.() -> Unit {
            return {
                number<Long>("id")
                date("start")
                date("finish")
                text("title")
                text("shortText")
                text("longText")
                bool("published")


                text("street")
                text("streetNumber")
                text("zip")
                text("city")
                text("country")

                geoPoint("geo")

                bool("hasSpaceLeft")
                number<Int>("maxGuestAmount")
                number<Int>("amountAccepted")
                number<Int>("amountOnWaitingList")
                number<Int>("remainingSpace")

                number<Long>("participant")

                text("categories")
            }
        }

        fun buildQuery(actor: Account, request: EventSearchRequest): ESQuery {
            return SearchDSL().bool {
                if (request.fullTextSearch.isNotBlank()) must(
                    SearchDSL().simpleQueryString(
                        request.fullTextSearch,
                        EventSearchEntryData::title,
                        EventSearchEntryData::shortText,
                        EventSearchEntryData::longText
                    )
                )

                if (request.from != null) must(
                    SearchDSL().range(EventSearchEntryData::start) {
                        gte = request.from
                    }
                )

                if (request.to != null) must(
                    SearchDSL().range(EventSearchEntryData::start) {
                        lte = request.to
                    }
                )

                if (request.ownEvents) filter(
                    SearchDSL().match(EventSearchEntryData::owner, actor.id.toString())
                )

                if (request.participatingEvents) filter(
                    SearchDSL().match(
                        EventSearchEntryData::participant,
                        actor.id.toString()
                    )
                )
            }
        }
    }


    fun convert(owner: AccountInfo): EventSearchEntry {
        return EventSearchEntry(
            id,
            start,
            finish,
            title,
            shortText,
            longText,
            published,
            owner,
            hasLocation,
            street ?: "",
            streetNumber ?: "",
            zip ?: "",
            city ?: "",
            country ?: "",
            lat ?: 0.0,
            lon ?: 0.0,
            hasSpaceLeft,
            maxGuestAmount,
            amountAccepted,
            amountOnWaitingList,
            remainingSpace,
            this.owner == owner.id,
            participant.contains(owner.id),
            categories
        )
    }

}
