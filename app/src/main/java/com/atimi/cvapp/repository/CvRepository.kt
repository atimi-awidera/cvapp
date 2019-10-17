package com.atimi.cvapp.repository

import com.atimi.cvapp.model.CvDocument
import com.atimi.cvapp.model.CvEntry
import com.atimi.cvapp.model.PersonalDetails
import com.atimi.cvapp.model.PersonalStatement

class CvRepository {

    private var entryCount = 20

    fun getCvEntries(): List<CvEntry> {
        val personalDetails = PersonalDetails("Donald", "Duck")
        val personalStatement = PersonalStatement("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec eget convallis metus. Nunc sodales enim at velit faucibus, ut congue felis rutrum. Donec sem libero, viverra quis velit eu, molestie dignissim lectus. Maecenas sit amet ullamcorper lectus. Integer ac justo vitae ante consectetur ultrices semper vel augue. Duis mauris velit, gravida non mi vitae, aliquet interdum orci. Proin purus enim, congue iaculis tincidunt non, cursus a massa.")

        val cvDocument = CvDocument(personalDetails, personalStatement)
        return cvDocument.getEntries()
    }
}