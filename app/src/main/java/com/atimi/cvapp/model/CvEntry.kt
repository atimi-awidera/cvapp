package com.atimi.cvapp.model

interface CvEntry {
    //Type is a method so it does not appear in the json
    fun getType():String
}