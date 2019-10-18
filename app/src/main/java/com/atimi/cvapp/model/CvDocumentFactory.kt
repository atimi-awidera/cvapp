package com.atimi.cvapp.model

import com.google.gson.Gson
import java.lang.Exception

/**
 * Factory method for generating CvDocument instances
 *
 * Currently it only supports CvDocument, this can be extented to create documents based
 * on xml, markdown, word documents etc
 *
 * This should be an implementing an interface if we ever wanted to mock it
 */
class CvDocumentFactory {
    val gson: Gson = Gson()

    private val TAG = "CvDocumentFactory"

    /**
     * Create an instance of CvDocument out of it's JSON representation
     *
     * This method would try to parse the provided string and construct the CvDocument out of it,
     * then it faile it'll return an empty document
     *
     * @return valid CvDocument or an empty CvDocument when failed
     */
    fun fromJSONString(jsonString: String): CvDocument {
        var document: CvDocument
        try {
            document = gson.fromJson(jsonString, CvDocument::class.java)
        } catch (e: Exception) {
            //Return an empty document if there was problem with parsing
            //General exception - should be using more specific errors
            document = CvDocument()
        }
        return document
    }
}
