package com.atimi.cvapp.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.atimi.cvapp.R
import com.atimi.cvapp.databinding.CvFragmentBinding
import com.atimi.cvapp.presentation.CvAdapter
import kotlinx.android.synthetic.main.cv_fragment.*

class CvFragment : Fragment() {

    companion object {
        fun newInstance() = CvFragment()
    }

    private lateinit var binding: CvFragmentBinding
    private lateinit var cvViewModel: CvViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.cv_fragment, container, false)

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
    }

}
