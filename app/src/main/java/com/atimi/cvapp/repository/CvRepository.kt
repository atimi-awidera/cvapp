package com.atimi.cvapp.repository

import android.content.Context
import android.os.Environment
import android.util.Log
import com.atimi.cvapp.R
import com.atimi.cvapp.model.CvDocument
import com.atimi.cvapp.model.CvDocumentFactory
import com.atimi.cvapp.model.CvEntry
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.*
import java.net.URL

class CvRepository {

    private val TAG = "CvRepository"

    var document:CvDocument = CvDocument()

    fun getCvEntries(): List<CvEntry> {
        return document.getEntries()
    }

    fun clean(context: Context) {
        val jsonFile = File(context.getExternalFilesDir(
            Environment.DIRECTORY_DOCUMENTS), "cv.json")
        if (jsonFile.exists() && jsonFile.isFile) {
            jsonFile.delete()
        }
    }

    fun isValid(context: Context) : Boolean {
        val jsonFile = File(context.getExternalFilesDir(
            Environment.DIRECTORY_DOCUMENTS), "cv.json")
        return jsonFile.exists() && jsonFile.isFile
    }

    fun refresh(context: Context, callback: OnDocumentReadyCallback) {
        if (isValid(context)) {
            restore(context, callback)
        } else {
            doAsync {
                val tmpDir = context.getCacheDir()
                val tmpFile = File.createTempFile("downloaded_cv", "json", tmpDir)
                //Having URL inside R provides possibility for different resumes for various locales
                val url = URL(context.getString(R.string.remote_cv_url))
                val inputStream = BufferedReader(InputStreamReader(url.openStream()))
                var line: String?
                try {
                    val out = FileOutputStream(tmpFile)
                    while (true) {
                        line = inputStream.readLine()
                        if (line == null) {
                            break
                        }
                        // It would be worth to sanitize the data at that stage even before
                        // anything is written, for example checking for invalid characters here
                        out.write(line.toByteArray())
                    }
                    inputStream.close()
                    out.close()
                    //Once file is correctly downloaded to the tmp move it to the documents directory
                    val outFile = File(
                        context.getExternalFilesDir(
                            Environment.DIRECTORY_DOCUMENTS
                        ), "cv.json"
                    )
                    if (outFile.exists()) {
                        outFile.delete()
                    }
                    tmpFile.copyTo(outFile)
                    tmpFile.delete()
                } catch (e: IOException) {
                    Log.d(TAG, "Failed to properly download file")
                }
                restore(context, callback)
            }
        }
    }

    fun restore(context: Context, callback: OnDocumentReadyCallback) {
        doAsync {
            val jsonFile = File(context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS), "cv.json")
            val jsonString = jsonFile.readText()
            //This can fail, needs checking and proper error handling
            val cvDocument = CvDocumentFactory().fromJSONString(jsonString)
            document = cvDocument
            uiThread {
                callback.onDocumentReady(document)
            }
        }
    }
}

interface OnDocumentReadyCallback {
    fun onDocumentReady(document: CvDocument)
}