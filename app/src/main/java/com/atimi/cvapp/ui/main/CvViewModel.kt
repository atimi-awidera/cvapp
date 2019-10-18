package com.atimi.cvapp.ui.main

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.atimi.cvapp.model.CvDocument
import com.atimi.cvapp.model.CvEntry
import com.atimi.cvapp.repository.CvRepository
import com.atimi.cvapp.repository.OnDocumentReadyCallback

class CvViewModel : ViewModel() {

    var repository: CvRepository = CvRepository()
    val entries: MutableLiveData<List<CvEntry>> =
        MutableLiveData<List<CvEntry>>().apply { value = repository.getCvEntries() }
    lateinit var readyCallback: () -> Unit

    val onDocumentReadyCallback = object : OnDocumentReadyCallback {
        override fun onDocumentReady(document: CvDocument) {
            entries.value = repository.getCvEntries()
            readyCallback()
        }
    }

    fun refresh(context: Context, readyCallback: () -> Unit) {
        //A bit hacky, assumes there's only one listener at a time
        this.readyCallback = readyCallback
        repository.refresh(context, onDocumentReadyCallback)
    }

}
