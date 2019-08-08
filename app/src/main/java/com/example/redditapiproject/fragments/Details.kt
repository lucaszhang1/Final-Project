package com.example.redditapiproject.fragments


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels

import com.example.redditapiproject.R
import com.example.redditapiproject.viewmodels.SubInfoListViewModel

/**
 * A simple [Fragment] subclass.
 *
 */
class Details : Fragment() {

    private val viewModel: SubInfoListViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.setNoRefresh(true)
    }

}
