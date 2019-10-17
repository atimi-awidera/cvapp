package com.atimi.cvapp.model

import com.google.gson.Gson

class CvDocumentFactory {

    val gson:Gson = Gson()

    fun fromJSONString(jsonString: String) : CvDocument {
        return gson.fromJson(jsonString, CvDocument::class.java)
    }

}
