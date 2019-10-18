package com.atimi.cvapp.model

/**
 * Class representing personal details of the person
 *
 * This class can be extented with other fields as necessary to include sex, date of birth, nationality
 * contact details etc, depending on what's required by the HR system
 *
 * @param name first name of the person
 * @param surname second name of the person
 */
data class PersonalDetails(val name:String, val surname:String): CvEntry {
    override fun getType() : String {
        return "Personal Details"
    }
}

/**
 * Class providing place holder for the personal statement
 *
 * This is the place where the person can write few words for the prospective employers
 *
 * @param statement few words about yourself
 */
data class PersonalStatement(val statement:String): CvEntry {
    override fun getType() : String {
        return "Personal Statement"
    }
}

/**
 * Class that specifies how work experience should be entitled
 *
 * While you might assume that it should always be work experience it might be different
 * If the text is prepared for another localization
 *
 * @param title How to entitle the Work Experience
 */
data class ExperienceHeader(val title:String): CvEntry {
    override fun getType() : String {
        return "Experience Header"
    }
}

/**
 * Class specifying each work experience record of the person
 *
 * Currently the work experience records are not sorted - they'll appear in the same order
 * as they appear in the source JSON file. It might be sensible to sort it later on
 *
 * @param company Name of the company the person is/was working for
 * @param timeframe What years the person was working at the company
 * @param description short description of this entry
 */
data class ExperienceEntry(val company: String, val timeframe: String, val description:String): CvEntry {
    override fun getType() : String {
        return "Experience Entry"
    }
}

/**
 * Class representing the Curriculum Vitae Document
 *
 * Convertible directly from JSON
 */
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
