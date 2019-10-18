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

/**
 * Middle layer above the data source
 *
 * Currently uses json file stored on the external storage to keep things simple, if the application
 * had to be bigger and let's say store more CVs consider using Room database
 */
class CvRepository {

    private val TAG = "CvRepository"
    private val CV_FILE_NAME = "cv.json"

    var document: CvDocument = CvDocument()

    /**
     * Returns current cv entries
     *
     * @return list of CvEntry records
     */
    fun getCvEntries(): List<CvEntry> {
        return document.getEntries()
    }

    /**
     * Cleans the current local cache
     *
     * In reality is just a file that has to be removed from disc
     *
     * @param context Requred for file operations
     */
    fun clean(context: Context) {
        val jsonFile = File(
            context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS
            ), CV_FILE_NAME
        )
        if (jsonFile.exists() && jsonFile.isFile) {
            jsonFile.delete()
        }
    }

    /**
     * Returns true if the local cache can be read
     *
     * @param context Requred for file operations
     *
     * @return true if the current cache is valid
     */
    fun isValid(context: Context): Boolean {
        val jsonFile = File(
            context.getExternalFilesDir(
                Environment.DIRECTORY_DOCUMENTS
            ), CV_FILE_NAME
        )
        return jsonFile.exists() && jsonFile.isFile
    }

    /**
     * Refresh the current cache if necessary from the URL address
     *
     * @param context Requred for file operations
     * @param callback Callback that will be triggered when the refresh is complete
     */
    fun refresh(context: Context, callback: OnDocumentReadyCallback) {
        if (isValid(context)) {
            restore(context, callback)
        } else {
            doAsync {
                //Assuming files are hoste on a secure location not public
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
                        // anything is written, for example checking for invalid characters
                        out.write(line.toByteArray())
                    }
                    inputStream.close()
                    out.close()
                    //Once file is correctly downloaded to the tmp move it to the documents directory
                    val outFile = File(
                        context.getExternalFilesDir(
                            Environment.DIRECTORY_DOCUMENTS
                        ), CV_FILE_NAME
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

    /**
     * Restore repository from the cached file
     *
     * @param context Requred for file operations
     * @param callback Callback that will be triggered when the restore is complete
     */
    fun restore(context: Context, callback: OnDocumentReadyCallback) {
        doAsync {
            val jsonFile = File(
                context.getExternalFilesDir(
                    Environment.DIRECTORY_DOCUMENTS
                ), CV_FILE_NAME
            )
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