package de.sambalmueslie.openevent.core.logic.export


import de.sambalmueslie.openevent.core.model.EventInfo
import org.apache.poi.ss.usermodel.BorderStyle
import org.apache.poi.ss.usermodel.FillPatternType
import org.apache.poi.ss.usermodel.HorizontalAlignment
import org.apache.poi.ss.usermodel.VerticalAlignment
import org.apache.poi.ss.util.CellRangeAddress
import org.apache.poi.xssf.usermodel.IndexedColorMap
import org.apache.poi.xssf.usermodel.XSSFColor
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.awt.Color
import java.time.format.DateTimeFormatter


class EventExcelSheetBuilder(
    private val wb: XSSFWorkbook,
    private val info: EventInfo
) {


    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EventExcelSheetBuilder::class.java)

        private val formatter = DateTimeFormatter.ofPattern("HH:mm")
        // Donnerstag, 16. März 2023
        private val headlineFormatter = DateTimeFormatter.ofPattern("EEEE ,dd. LLLL yyyy")
    }

    private val sheet = wb.createSheet(info.event.title)
    private var rowIndex = 3
    private val styleOfferHeaderBold = wb.createCellStyle()
    private val styleOfferHeaderBoldCombined = wb.createCellStyle()
    private val styleOfferHeaderText = wb.createCellStyle()
    private val styleBooking = wb.createCellStyle()
    private val styleBookingCombined = wb.createCellStyle()

    private val boldFont = wb.createFont()
    private val boldFontInverted = wb.createFont()
    private val normalFont = wb.createFont()
    private val colorMap: IndexedColorMap = wb.stylesSource.indexedColors

    fun build() {
        setupStyles()
        setupSheet()
        addTitle()
        addDescription()
        setupDetails()
        setupStripes()
    }


    private fun addTitle() {
        val row = sheet.createRow(1)
        val cell = row.createCell(0)
        cell.setCellValue(info.event.title)
        val font = wb.createFont()
        font.fontHeightInPoints = 14
        font.bold = true

        val borderStyle = wb.createCellStyle()
        borderStyle.borderBottom = BorderStyle.THIN
        borderStyle.setFont(font)
        cell.cellStyle = borderStyle

        (1..9).forEach { row.createCell(it).cellStyle = borderStyle }
        sheet.addMergedRegion(CellRangeAddress(row.rowNum, row.rowNum, 0, 9))
    }


    private fun addDescription() {
        val row = sheet.createRow(2)
        val cell = row.createCell(0)
        cell.setCellValue(info.event.title)
        val font = wb.createFont()
        font.fontHeightInPoints = 12

        (1..9).forEach { row.createCell(it) }
        sheet.addMergedRegion(CellRangeAddress(row.rowNum, row.rowNum, 0, 9))
    }


    private fun setupDetails() {
        var row = sheet.createRow(rowIndex++)


        TODO("Not yet implemented")
    }



    private fun setupStripes() {
//        TODO("Not yet implemented")
    }



    private fun setupStyles() {
        boldFont.bold = true
        boldFont.fontHeightInPoints = 12

        boldFontInverted.bold = true
        boldFontInverted.fontHeightInPoints = 12
        boldFontInverted.setColor(XSSFColor(Color(255, 255, 255), colorMap))

        normalFont.fontHeightInPoints = 12

        styleOfferHeaderBold.setFillForegroundColor(XSSFColor(Color(217, 217, 217), colorMap))
        styleOfferHeaderBold.fillPattern = FillPatternType.SOLID_FOREGROUND
        styleOfferHeaderBold.setFont(boldFont)
        styleOfferHeaderBold.borderBottom = BorderStyle.THIN
        styleOfferHeaderBold.alignment = HorizontalAlignment.CENTER

        styleOfferHeaderBoldCombined.setFillForegroundColor(XSSFColor(Color(217, 217, 217), colorMap))
        styleOfferHeaderBoldCombined.fillPattern = FillPatternType.SOLID_FOREGROUND
        styleOfferHeaderBoldCombined.setFont(boldFont)
        styleOfferHeaderBoldCombined.borderBottom = BorderStyle.THIN
        styleOfferHeaderBoldCombined.wrapText = true
        styleOfferHeaderBoldCombined.verticalAlignment = VerticalAlignment.CENTER
        styleOfferHeaderBoldCombined.alignment = HorizontalAlignment.CENTER

        styleOfferHeaderText.setFillForegroundColor(XSSFColor(Color(217, 217, 217), colorMap))
        styleOfferHeaderText.fillPattern = FillPatternType.SOLID_FOREGROUND
        styleOfferHeaderText.alignment = HorizontalAlignment.RIGHT
        styleOfferHeaderText.borderBottom = BorderStyle.THIN
        styleOfferHeaderText.setFont(normalFont)

        styleBooking.fillPattern = FillPatternType.NO_FILL
        styleBooking.borderBottom = BorderStyle.THIN
        styleBooking.verticalAlignment = VerticalAlignment.CENTER
        styleBooking.alignment = HorizontalAlignment.CENTER
        styleBooking.setFont(normalFont)

        styleBookingCombined.fillPattern = FillPatternType.NO_FILL
        styleBookingCombined.borderBottom = BorderStyle.THIN
        styleBookingCombined.wrapText = true
        styleBookingCombined.verticalAlignment = VerticalAlignment.CENTER
        styleBookingCombined.alignment = HorizontalAlignment.CENTER
        styleBookingCombined.setFont(normalFont)

    }

    private fun setupSheet() {
        sheet.setColumnWidth(0, 5 * 256)
        sheet.setColumnWidth(1, 9 * 256)
        sheet.setColumnWidth(2, 9 * 256)
        sheet.setColumnWidth(3, 14 * 256)
        sheet.setColumnWidth(4, 12 * 256)
        sheet.setColumnWidth(5, 9 * 256)
        sheet.setColumnWidth(6, 5 * 256)
        sheet.setColumnWidth(7, 9 * 256)
        sheet.setColumnWidth(8, 9 * 256)
        sheet.setColumnWidth(9, 15 * 256)
    }
}
