package com.atimi.cvapp.model

/**
 * Interface common to all entries that are displayed in the CV
 *
 * It should have only one method that specifies the type of the entry, for simplicity it uses
 * String as this is MVP, a more thorough and bullet-proof approach would be to use an enum
 */
interface CvEntry {
    /**
     * Get the type of the entry
     *
     * This method returns string representing type of the entry, it should be implemented by
     * each CvEntry to help the CvAdapter in deciding how to deal with each entry.
     *
     * @return string representing type of the entry
     */
    fun getType():String
}