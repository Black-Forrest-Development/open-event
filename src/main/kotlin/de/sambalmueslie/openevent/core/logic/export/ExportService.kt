package de.sambalmueslie.openevent.core.logic.export


import de.sambalmueslie.openevent.core.logic.event.EventCrudService
import de.sambalmueslie.openevent.core.model.Account
import de.sambalmueslie.openevent.storage.util.PageableSequence
import io.micronaut.http.server.types.files.SystemFile
import jakarta.inject.Singleton
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@Singleton
class ExportService(
    private val eventService: EventCrudService,
    private val excelExporter: EventExcelExporter,
    private val pdfExporter: EventPdfExporter
) {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(ExportService::class.java)
    }

    fun exportEventsPdf(account: Account): SystemFile? {
        return exportEvents(account, pdfExporter)
    }

    fun exportEventsExcel(account: Account): SystemFile? {
        return exportEvents(account, excelExporter)
    }

    private fun exportEvents(account: Account, exporter: EventExporter): SystemFile? {
        return exporter.exportEvents { PageableSequence { eventService.getInfos(it) } }
    }

    fun exportEventExcel(eventId: Long, account: Account): SystemFile? {
        return exportEvent(eventId, account, excelExporter)
    }

    fun exportEventPdf(eventId: Long, account: Account): SystemFile? {
        return exportEvent(eventId, account, pdfExporter)
    }

    private fun exportEvent(eventId: Long, account: Account, exporter: EventExporter): SystemFile? {
        val info = eventService.getInfo(eventId, account) ?: return null
        return exporter.exportEvent(info)
    }
}
