package de.sambalmueslie.openevent.core.logic.export


import com.google.zxing.BarcodeFormat
import com.google.zxing.client.j2se.MatrixToImageWriter
import com.google.zxing.qrcode.QRCodeWriter
import de.sambalmueslie.openevent.core.model.EventInfo
import de.sambalmueslie.openevent.infrastructure.time.TimeProvider
import io.micronaut.core.io.ResourceLoader
import io.micronaut.http.server.types.files.SystemFile
import jakarta.inject.Singleton
import org.apache.fop.apps.FopFactory
import org.apache.fop.apps.MimeConstants
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.VelocityEngine
import org.apache.velocity.tools.generic.EscapeTool
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.InputStreamReader
import java.io.StringWriter
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import javax.imageio.ImageIO
import javax.xml.transform.TransformerFactory
import javax.xml.transform.sax.SAXResult
import javax.xml.transform.stream.StreamSource
import kotlin.jvm.optionals.getOrNull

@Singleton
class EventPdfExporter(
    private val loader: ResourceLoader,
    private val timeProvider: TimeProvider
) : EventExporter {

    companion object {
        private val logger: Logger = LoggerFactory.getLogger(EventPdfExporter::class.java)
        private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
        private const val HEADER_PDF_FILE_SUFIX = ".pdf"
        private const val HEADER_PDF_FILE_PREFIX = "events"
    }

    private val config = loader.getResource("classpath:fop/fop.xconf").getOrNull()!!
    private val fopFactory: FopFactory = FopFactory.newInstance(config.toURI())
    private val ve = VelocityEngine()

    init {
        ve.init()
    }

    override fun exportEvents(provider: () -> Sequence<EventInfo>): SystemFile? {
        TODO("Not yet implemented")
    }

    override fun exportEvent(info: EventInfo): SystemFile? {

        val barcodeWriter = QRCodeWriter()
        val url = "https://open.psm.church/event/details/" + info.event.id
        val bitMatrix = barcodeWriter.encode(url, BarcodeFormat.QR_CODE, 250, 250)
        val qrCodeImage = MatrixToImageWriter.toBufferedImage(bitMatrix)
        val qrCodeByteArray = ByteArrayOutputStream()
        ImageIO.write(qrCodeImage, "png", qrCodeByteArray)

        val registration = info.registration
        val availableSpace = registration?.let {
            generateSequence { 's' }.take(6).toList()
        } ?: emptyList<Char>()
        val properties = mapOf(
            Pair("esc", EscapeTool()),
            Pair("user", info.event.owner),
            Pair("info", info),
            Pair("availableSpace", availableSpace),
            Pair("qr", Base64.getEncoder().encodeToString(qrCodeByteArray.toByteArray())),
            Pair("date", formatter.format(LocalDateTime.now(ZoneId.of("Europe/Berlin"))))
        )
        val context = VelocityContext(properties)
        val writer = StringWriter()


        ve.evaluate(
            context,
            writer,
            "PDF Export",
            InputStreamReader(loader.getResourceAsStream("classpath:fop/event1.vm").getOrNull()!!)
        )

        val out = ByteArrayOutputStream()
        val fop = fopFactory.newFop(MimeConstants.MIME_PDF, out)
        val factory = TransformerFactory.newInstance()
        val transformer = factory.newTransformer()
        val src = StreamSource(writer.toString().byteInputStream())
        val res = SAXResult(fop.defaultHandler)
        transformer.transform(src, res)

        val file = File.createTempFile(
            HEADER_PDF_FILE_SUFIX,
            HEADER_PDF_FILE_PREFIX
        )
        file.writeBytes(out.toByteArray())
        val date = timeProvider.now().toLocalDate()
        val filename = "${date}-event-export.pdf"
        return SystemFile(file).attach(filename)
    }


}
