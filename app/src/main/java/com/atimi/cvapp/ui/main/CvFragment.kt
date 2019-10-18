package com.atimi.cvapp.ui.main

import android.Manifest
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.PermissionChecker
import androidx.core.content.PermissionChecker.checkSelfPermission
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.atimi.cvapp.databinding.CvFragmentBinding
import com.atimi.cvapp.presentation.CvAdapter
import kotlinx.android.synthetic.main.cv_fragment.*

class CvFragment() : Fragment(), OnRefreshListener {

    private val TAG = "CvFragment"
    private val WRITE_EXTERNAL_REQUEST_CODE = 100

    companion object {
        fun newInstance() = CvFragment()
    }

    private lateinit var binding: CvFragmentBinding
    private lateinit var cvViewModel: CvViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, com.atimi.cvapp.R.layout.cv_fragment, container, false)

        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        cvViewModel = ViewModelProviders.of(this).get(CvViewModel::class.java)

        val layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        recyclerView.hasFixedSize()
        recyclerView.adapter = CvAdapter()

        binding.cvViewModel = cvViewModel


        //Verify that the external strage is mounted and available for reading and writing
        if (!isExternalStorageReadable() || !isExternalStorageWritable()) {
            //This is an error application should not continue
            Log.d(TAG,"Failed to find external storage")
        } else {
            if (cvViewModel.repository.isValid(requireContext())) {
                refreshCache()
            } else {
                // There's no valid file, check if you have network permission,
                // trigger download of file if necessary
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(
                            this.requireContext(),
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        ) == PermissionChecker.PERMISSION_DENIED
                    ) {
                        requestPermissions(
                            arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                            WRITE_EXTERNAL_REQUEST_CODE
                        )
                    } else {
                        //Runtime permission already granted
                        refreshCache()
                    }
                } else {
                    //No need for runtime permission, version older than Marshmallow
                    refreshCache()
                }
            }
        }

        main.setOnRefreshListener(this)

    }

    override fun onRefresh() {
        cvViewModel.repository.clean(requireContext())
        refreshCache()
    }

    fun isExternalStorageWritable(): Boolean {
        return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
    }

    fun isExternalStorageReadable(): Boolean {
        return Environment.getExternalStorageState() in
                setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
    }

    fun refreshCache() {
        cvViewModel.refresh(requireContext()) {
            main.isRefreshing=false
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode != WRITE_EXTERNAL_REQUEST_CODE) {
            return
        }
        grantResults.forEach {
            if(it != PermissionChecker.PERMISSION_GRANTED)
            {
                Log.d(TAG,"Failed to acquire sufficient permissions")
                //A bit brute force, might be worth putting at least some alert here
                activity?.finishAndRemoveTask()
            }
        }
        refreshCache()
    }

}
