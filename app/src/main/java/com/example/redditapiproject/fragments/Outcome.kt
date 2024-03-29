package com.example.redditapiproject.fragments


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ctdrecviewtutorial.adapter.SubInfoRecycleViewAdapter
import com.example.redditapiproject.R
import com.example.redditapiproject.models.SubInfo
import com.example.redditapiproject.models.SubmissionListing
import com.example.redditapiproject.network.APICommunitySubmissionRestClient
import com.example.redditapiproject.network.APIUserSubmissionRestClient
import com.example.redditapiproject.network.RetrofitEventListener
import com.example.redditapiproject.viewmodels.SubInfoListViewModel
import kotlinx.android.synthetic.main.fragment_outcome.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import retrofit2.Call

/**
 * A simple [Fragment] subclass.
 *
 */
class Outcome : Fragment() {

    private val viewModel: SubInfoListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_outcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val subName = arguments?.get(getString(R.string.subreddit_name))!! as String

        val adapter = SubInfoRecycleViewAdapter { position ->
            val bundle = bundleOf(getString(R.string.position_key) to position)
            findNavController().navigate(R.id.action_outcome_to_details, bundle)
        }

        val myLayoutManager = LinearLayoutManager(requireContext())

        recyclerView.apply {
            this.layoutManager = myLayoutManager
            this.adapter = adapter
        }

        viewModel.submissionList.observe(this, Observer {
            it?.let { subList ->
                adapter.submitList(subList)
            }
        })

        if(viewModel.norefresh.value == false)
            displayTestData(adapter, subName)

    }

    private fun displayTestData(adapter: SubInfoRecycleViewAdapter, subName: String) {
        val x = GlobalScope.launch {
            Log.d("RUNBLOCK", "running blocking in dislpayTestData")
            val data = mutableListOf<SubInfo>()
            viewModel.clearSubList()
            val channel = getTopSubHits(subName)
            for (s in channel) {
                val (sub, hits, count) = s
                Log.d("SUB", "Sub found: $sub, $hits, $count")
                data.add(s)
            }
            viewModel.setSubList(data.filter {it.userCount > 1}.sortedByDescending { it.hits })
        }
    }

    private fun getTopSubHits(subName: String): Channel<SubInfo> {
        val chan = Channel<SubInfo>()
        APICommunitySubmissionRestClient.instance.getSubmissionList(subName, 45, object : RetrofitEventListener {
            override fun onSuccess(call: Call<*>?, response: Any) {
                val users = mutableSetOf<String>()

                if (response is SubmissionListing) {
                    for (x in response.data?.children!!) {
                        users.add(x.data?.author!!)
                        Log.d("USERS", "Added user")
                    }

                    GlobalScope.launch {
                        val hitMap = mutableMapOf<String, Pair<Int, Int>>()

                        for (user in users) {
                            for ((subreddit, hits) in getUserHits(user)) {
                                hitMap.modifyOrDefault(subreddit, Pair(hits, 1)) {
                                    Pair(it.first + hits, it.second + 1)
                                }
                                viewModel.addUserToSub(user, subreddit)
                            }
                        }

                        for ((sub, x) in hitMap) chan.send(SubInfo(sub, x.first, x.second))
                        chan.close()
                        Log.d("CHAN", "USERHIT CHANNEL CLOSED")
                    }
                }
            }


            override fun onError(call: Call<*>?, t: Throwable?) {
                throw t ?: Throwable("Unknown network error!")
            }
        })
        Log.d("CHAN", "CHAN SENT")
        return chan
    }

    fun getUserHits(user: String): Channel<Pair<String, Int>> {
        val chan = Channel<Pair<String, Int>>()

        APIUserSubmissionRestClient.instance.getSubmissionList(user, object : RetrofitEventListener {
            override fun onSuccess(call: Call<*>?, response: Any?) {
                val hitMap = mutableMapOf<String, Int>()

                if (response is SubmissionListing) {
                    Log.d("NETWORK", "Got a user: $user")
                    for (x in response.data?.children!!) {
                        hitMap.modifyOrDefault(x.data?.subreddit!!, 1) {
                            it + 1
                        }
                    }
                } else {
                    chan.close()
                }
                GlobalScope.launch {
                    for (kvp in hitMap) chan.send(kvp.toPair())
                    chan.close()
                }
            }

            override fun onError(call: Call<*>?, t: Throwable?) {
                throw t ?: Throwable("Unknown network error!")
            }
        })
        return chan

    }

    private fun <K, V> MutableMap<K, V>.modifyOrDefault(key: K, default: V, mod: (V) -> V) {
        val cur = this[key]
        this[key] = cur?.let { mod(it) } ?: default
    }

}

