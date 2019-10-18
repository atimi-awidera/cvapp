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

    @Test
    fun fromJSONString_complexIsCorrect() {
        val personalDetails = PersonalDetails("A", "B")
        val personalStatement = PersonalStatement("C")
        val experienceHeader = ExperienceHeader("W")
        val experienceEntries = mutableListOf<ExperienceEntry>()
        experienceEntries.add(ExperienceEntry("1"))
        experienceEntries.add(ExperienceEntry("2"))
        experienceEntries.add(ExperienceEntry("3"))
        experienceEntries.add(ExperienceEntry("4"))
        experienceEntries.add(ExperienceEntry("5"))
        val cvDocument = CvDocument(personalDetails, personalStatement, experienceHeader, experienceEntries)
        var cvJSONDocument = documentFactory.fromJSONString("""
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
                    { description="1" },
                    { description="2" },
                    { description="3" },
                    { description="4" },
                    { description="5" }
                ]
            }
        """.trimIndent())
        Assert.assertEquals(cvDocument, cvJSONDocument)
    }
}
