package com.atimi.cvapp.repository

import com.atimi.cvapp.model.CvDocument
import com.atimi.cvapp.model.CvEntry
import com.atimi.cvapp.model.PersonalDetails

class CvRepository {

    private var entryCount = 20

    fun getCvEntries(): List<CvEntry> {
        val personalDetails = PersonalDetails("Donald", "Duck")
        val cvDocument = CvDocument(personalDetails)
        return cvDocument.getEntries()
    }
}