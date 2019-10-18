package com.atimi.cvapp.model

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

data class ExperienceHeader(val title:String): CvEntry {
    override fun getType() : String {
        return "Experience Header"
    }
}

data class ExperienceEntry(val description:String): CvEntry {
    override fun getType() : String {
        return "Experience Entry"
    }
}

data class CvDocument( val personalDetails: PersonalDetails? = null,
                       val personalStatement: PersonalStatement? = null,
                       val experienceHeader: ExperienceHeader? = null,
                       val experienceEntries: List<ExperienceEntry>? = null) {

    /**
     * Flaten the document into entries that can be displayed in the layout
     *
     * This method returns a flattened set of entries that can be accessed in sequential fashion
     *
     * @return List of CvEntry entris that can be identified and duck typed to appropriate cells
     */
    fun getEntries() : List<CvEntry> {
        val entries = mutableListOf<CvEntry>()
        personalDetails.let { if (it != null) entries.add(it) }
        personalStatement.let { if (it != null) entries.add(it) }
        experienceHeader.let { if (it != null) entries.add(it) }
        experienceEntries.let { if (it != null) {
            it.forEach { entry -> entries.add(entry) }
        } }

        return entries
    }
}
