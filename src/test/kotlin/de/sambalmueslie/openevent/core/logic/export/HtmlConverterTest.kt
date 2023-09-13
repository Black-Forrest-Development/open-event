package de.sambalmueslie.openevent.core.logic.export

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class HtmlConverterTest {


    @Test()
    fun testConverter() {
        val content =
            "<p><strong>Fetter Text</strong></p><p><br></p><p><em>Kursiver Text</em></p><p><br></p><p><u>Unterstrichener Text</u></p><p><br></p><p><s>Durchgestrichener Text</s></p><p><br></p><p><span class=\"ql-size-huge\">Großer Text</span></p><p><br></p><h1>Überschrift 1</h1><p>Linksbündig</p><p class=\"ql-align-right\">Rechtsbündig</p><p class=\"ql-align-center\">Zentriert</p><p>adsfasdfd</p><p>asdfsadfsadf</p><p>adsf</p><p><br></p>"
        val converter = HtmlConverter()

        assertEquals("<fo:block font-size=\"12pt\">Normaler Text</fo:block>\n", converter.convert("<p>Normaler Text</p>"))
        assertEquals("<fo:block font-weight=\"bold\" font-size=\"12pt\">Fetter Text</fo:block>\n", converter.convert("<p><strong>Fetter Text</strong></p>"))
        assertEquals("<fo:block></fo:block>\n", converter.convert("<p><br></p>"))
        assertEquals("<fo:block font-style=\"italic\" font-size=\"12pt\">Kursiver Text</fo:block>\n", converter.convert("<p><em>Kursiver Text</em></p>"))
        assertEquals("<fo:block font-size=\"12pt\">Unterstrichener Text</fo:block>\n", converter.convert("<p><u>Unterstrichener Text</u></p>"))
        assertEquals("<fo:block font-size=\"12pt\">Durchgestrichener Text</fo:block>\n", converter.convert("<p><s>Durchgestrichener Text</s></p>"))
    }
}
