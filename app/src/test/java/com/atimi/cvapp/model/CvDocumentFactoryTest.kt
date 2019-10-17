package com.atimi.cvapp.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ExampleUnitTest {

    private lateinit var documentFactory: CvDocumentFactory

    @Before
    fun init() {
        documentFactory = CvDocumentFactory()
    }

    @Test
    fun fromJSONString_isCorrect() {
        val personalDetails = PersonalDetails("A", "B")
        val personalStatement = PersonalStatement("C")
        val cvDocument = CvDocument(personalDetails, personalStatement)

        var cvJSONDocument = documentFactory.fromJSONString("""
            {
                personalDetails: {
                    name:"A",
                    surname:"B"
                },
                personalStatement: {
                    statement:"C"
                }
            }
        """.trimIndent())
        Assert.assertEquals(cvDocument, cvJSONDocument)
    }
}
