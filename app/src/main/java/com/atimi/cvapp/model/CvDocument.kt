package com.atimi.cvapp.model

import kotlin.reflect.KClass

data class PersonalDetails(val name:String, val surname:String): CvEntry {
    override fun getType() : String {
        return "Personal Details"
    }
}

data class PersonalStatement(val statement:String): CvEntry {
    override fun getType() : String {
        return "Personal Statement"
    }
}

data class CvDocument( val personalDetails: PersonalDetails? = null,
                       val personalStatement: PersonalStatement? = null) {
    fun getEntries() : List<CvEntry> {
        val entries = mutableListOf<CvEntry>()
        personalDetails.let { if (it != null) entries.add(it) }
        personalStatement.let { if (it != null) entries.add(it) }
        return entries
    }
}
