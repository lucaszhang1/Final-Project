package com.example.redditapiproject.fragments


import android.os.Bundle
import android.telephony.SubscriptionInfo
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctdrecviewtutorial.adapter.SubInfoRecycleViewAdapter
import com.example.redditapiproject.R
import com.example.redditapiproject.models.SubInfo
import kotlinx.android.synthetic.main.fragment_outcome.*

/**
 * A simple [Fragment] subclass.
 *
 */
class Outcome : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_outcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val adapter = SubInfoRecycleViewAdapter {

        }

        val myLayoutManager = LinearLayoutManager(requireContext())

        recyclerView.apply {
            this.layoutManager = myLayoutManager
            this.adapter = adapter
        }

        displayTestData(adapter)

    }

    private fun displayTestData(adapter: SubInfoRecycleViewAdapter) {
        val testData = listOf(
            SubInfo("A", 85000, 123),
            SubInfo("B", 85000, 123),
            SubInfo("C", 85000, 123))
        adapter.submitList(testData)
    }
}
