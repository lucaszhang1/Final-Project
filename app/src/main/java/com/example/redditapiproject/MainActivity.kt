package com.example.redditapiproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.redditapiproject.models.SubmissionListing
import com.example.redditapiproject.network.ApiUserRestClient
import com.example.redditapiproject.network.RetrofitEventListener
import retrofit2.Call

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSubmissions()
    }

    private fun getSubmissions() {
        ApiUserRestClient.instance.getSubmissionList(object : RetrofitEventListener {
            override fun onSuccess(call: Call<*>?, response: Any) {
                if (response is SubmissionListing) {
                    Log.d("NETWORK", "Got a sub:")
                    for (x in response.data?.children!!) {
                        Log.d("SUBREDDIT","\tPost found: user: ${x.data?.author}")
                    }
                }
            }

            override fun onError(call: Call<*>?, t: Throwable?) {
                Log.e("NETWORK ERROR", "onError: ${t?.message.toString()}")
            }
        })
    }
}
