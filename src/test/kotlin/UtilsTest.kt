package my.jdbc.wsdl_driver

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class UtilsTest {
    @Test
    fun `escapeXml omits XML declaration and escapes text`() {
        assertEquals("&lt;tag&gt;&amp;", escapeXml("<tag>&"))
    }

    @Test
    fun `parseXml error includes surrounding lines`() {
        val xml = """
            <root>
            <beforeFirst>earlier</beforeFirst>
            <before>first</before>
            <broken>value</root>
            <after>last</after>
            <afterLast>later</afterLast>
            </root>
        """.trimIndent()

        val exception = org.junit.jupiter.api.Assertions.assertThrows(
            java.sql.SQLException::class.java
        ) {
            parseXml(xml)
        }

        assertTrue(
            exception.message!!.contains(
                "Problematic line surrounding:\n" +
                    "<beforeFirst>earlier</beforeFirst>\n" +
                    "<before>first</before>\n" +
                    "<broken>value</root>\n" +
                    "<after>last</after>\n" +
                    "<afterLast>later</afterLast>"
            )
        )
    }
}
