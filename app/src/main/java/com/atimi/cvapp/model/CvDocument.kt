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

data class CvDocument( val personalDetails: PersonalDetails,
                       val personalStatement: PersonalStatement) {
    fun getEntries() : List<CvEntry> {
        val entries = mutableListOf<CvEntry>()
        entries.add(personalDetails)
        entries.add(personalStatement)
        return entries
    }
}

//Extension to get the class name
val<T: Any> T.kClass: KClass<T> get() = javaClass.kotlin