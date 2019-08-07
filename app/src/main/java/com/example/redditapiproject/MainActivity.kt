package com.example.redditapiproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.redditapiproject.models.SubmissionListing
import com.example.redditapiproject.network.APICommunitySubmissionRestClient
import com.example.redditapiproject.network.APIUserSubmissionRestClient
import com.example.redditapiproject.network.RetrofitEventListener
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import retrofit2.Call

private fun <K, V> MutableMap<K, V>.modifyOrDefault(key: K, default: V, mod: (V) -> V) {
    val cur = this[key]
    this[key] = when (cur) {
        null -> default
        else -> mod(cur)
    }
}

data class SubInfoDebug(val name: String, val hits: Int, val userCount: Int )

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            for ((sub, hits, count) in getTopSubHits()) {
                Log.d("SUB HITS", "$sub: \t\t\t$hits - $count")
            }
        }
    }

    private fun getTopSubHits(): Channel<SubInfoDebug> {
        val chan = Channel<SubInfoDebug>()
        APICommunitySubmissionRestClient.instance.getSubmissionList(object : RetrofitEventListener {
            override fun onSuccess(call: Call<*>?, response: Any) {
                val users = mutableSetOf<String>()

                if (response is SubmissionListing) {
                    Log.d("NETWORK", "Got a subreddit:")
                    for (x in response.data?.children!!) {
                        users.add(x.data?.author!!)
                    }

                    GlobalScope.launch {
                        val hitMap = mutableMapOf<String, Pair<Int, Int>>()

                        for (user in users) {
                            for ((subreddit, hits) in getUserHits(user)) {
                                hitMap.modifyOrDefault(subreddit, Pair(hits, 1)) {
                                    Pair(it.first + hits, it.second + 1)
                                }
                            }
                        }

                        for ((sub, x) in hitMap) chan.send(SubInfoDebug(sub, x.first, x.second))
                        chan.close()
                    }
                }
            }


            override fun onError(call: Call<*>?, t: Throwable?) {
                throw t ?: Throwable("Unknown network error!")
            }
        })
        return chan
    }

    fun getUserHits(user: String): Channel<Pair<String, Int>> {
        val chan = Channel<Pair<String, Int>>()

        APIUserSubmissionRestClient.instance.getSubmissionList(user, object : RetrofitEventListener {
            override fun onSuccess(call: Call<*>?, response: Any) {
                val hitMap = mutableMapOf<String, Int>()

                if (response is SubmissionListing) {
                    Log.d("NETWORK", "Got a user:")
                    for (x in response.data?.children!!) {
                        hitMap.modifyOrDefault(x.data?.subreddit!!, 1) {
                            it + 1
                        }
                    }
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
}
