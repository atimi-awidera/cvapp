package com.atimi.cvapp.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atimi.cvapp.model.CvEntry
import com.atimi.cvapp.repository.CvRepository

class CvViewModel : ViewModel() {

    val entries: LiveData<List<CvEntry>> =
        MutableLiveData<List<CvEntry>>().apply { value = CvRepository().getCvEntries() }

}
