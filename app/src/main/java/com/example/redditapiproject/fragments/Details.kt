package com.example.redditapiproject.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager

import com.example.redditapiproject.R
import com.example.redditapiproject.adapter.UserListRecycleViewAdapter
import com.example.redditapiproject.viewmodels.SubInfoListViewModel
import kotlinx.android.synthetic.main.fragment_detail.*

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

        val position = arguments?.get(getString(R.string.position_key)) as Int
        val subreddit = viewModel.submissionList.value!![position]

        subredditName.text = "r/${subreddit.name}"


        val myLayoutManager = LinearLayoutManager(requireContext())
        val adapter = UserListRecycleViewAdapter()

        userNameList.apply {
            this.adapter = adapter
            this.layoutManager = myLayoutManager
        }

        adapter.submitList(viewModel.subUserTable.value?.get(subreddit.name)?.toList()!!)
    }

}
