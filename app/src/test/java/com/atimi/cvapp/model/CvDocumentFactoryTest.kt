package com.atimi.cvapp.model

import org.junit.Assert
import org.junit.Before
import org.junit.Test

class CvDocumentFactoryTest {

    private lateinit var documentFactory: CvDocumentFactory

    @Before
    fun init() {
        documentFactory = CvDocumentFactory()
    }

    @Test
    fun fromJSONString_emptyIsCorrect() {
        val cvDocument = CvDocument()
        var cvJSONDocument = documentFactory.fromJSONString("""{}""".trimIndent())
        Assert.assertEquals(cvDocument, cvJSONDocument)
    }

    @Test
    fun fromJSONString_simpleIsCorrect() {
        val personalDetails = PersonalDetails("A", "B")
        val personalStatement = PersonalStatement("C")
        val cvDocument = CvDocument(personalDetails, personalStatement)

        var cvJSONDocument = documentFactory.fromJSONString(
            """
            {
                personalDetails: {
                    name:"A",
                    surname:"B"
                },
                personalStatement: {
                    statement:"C"
                }
            }
        """.trimIndent()
        )
        Assert.assertEquals(cvDocument, cvJSONDocument)
    }

    @Test
    fun fromJSONString_complexIsCorrect() {
        val personalDetails = PersonalDetails("A", "B")
        val personalStatement = PersonalStatement("C")
        val experienceHeader = ExperienceHeader("W")
        val experienceEntries = mutableListOf<ExperienceEntry>()
        experienceEntries.add(ExperienceEntry("A", "0-1", "A1"))
        experienceEntries.add(ExperienceEntry("B", "2-3", "B1"))
        experienceEntries.add(ExperienceEntry("C", "4-5", "C1"))
        experienceEntries.add(ExperienceEntry("D", "6-7", "D1"))
        experienceEntries.add(ExperienceEntry("E", "8-9", "E1"))
        val cvDocument =
            CvDocument(personalDetails, personalStatement, experienceHeader, experienceEntries)
        var cvJSONDocument = documentFactory.fromJSONString(
            """
            {
                personalDetails: {
                    name:"A",
                    surname:"B"
                },
                personalStatement: {
                    statement:"C"
                },
                experienceHeader: {
                    title: "W"
                },
                experienceEntries: [
                    { company="A", timeframe="0-1", description="A1" },
                    { company="B", timeframe="2-3", description="B1" },
                    { company="C", timeframe="4-5", description="C1" },
                    { company="D", timeframe="6-7", description="D1" },
                    { company="E", timeframe="8-9", description="E1" }
                ]
            }
        """.trimIndent()
        )
        Assert.assertEquals(cvDocument, cvJSONDocument)
    }

    @Test
    fun fromJSONString_malformedIsEmpty() {
        val cvDocument = CvDocument()
        var cvJSONDocument = documentFactory.fromJSONString("""{}AS}asd[]""".trimIndent())
        Assert.assertEquals(cvDocument, cvJSONDocument)
    }
}
