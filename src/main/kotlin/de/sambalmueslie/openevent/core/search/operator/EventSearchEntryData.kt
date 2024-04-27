package de.sambalmueslie.openevent.core.search.operator

import com.jillesvangurp.searchdsls.mappingdsl.FieldMappings
import de.sambalmueslie.openevent.core.event.api.EventInfo
import org.jsoup.Jsoup
import java.time.LocalDateTime

data class EventSearchEntryData(
    var id: String,
    var start: LocalDateTime,
    var finish: LocalDateTime,
    var title: String,
    var shortText: String,
    var longText: String,
    var published: Boolean,

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
    }

}
