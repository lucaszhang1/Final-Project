package com.example.redditapiproject.network

import android.util.Log
import com.example.redditapiproject.models.SubmissionListing

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ApiUserRestClient {

    companion object {
        val instance = ApiUserRestClient()
    }

    private var apiSubmission: APISubmission? = null

    /**
     * Invoke getUserList via [Call] request.
     * @param retrofitEventListener of RetrofitEventListener.
     */

    fun getSubmissionList(retrofitEventListener: RetrofitEventListener) {
        val retrofit = NetworkClient.retrofitClient
        apiSubmission = retrofit.create<APISubmission>(APISubmission::class.java)

        val data = hashMapOf<String, String>(
            "count" to "25"
        )

        val apiSubmissionCall = apiSubmission!!.getSubmissionList("aww", data)
        /*
        This is the line which actually sends a network request. Calling enqueue() executes a call asynchronously. It has two callback listeners which will invoked on the main thread
        */

        Log.d("NETWORK", "Enqueuing callbacks")

        apiSubmissionCall.enqueue(object : Callback<SubmissionListing> {

            override fun onResponse(call: Call<SubmissionListing>?, response: Response<SubmissionListing>?) {
                /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                 */
                response?.let {
                    retrofitEventListener.onSuccess(call, it.body())
                }
            }
            override fun onFailure(call: Call<SubmissionListing>?, t: Throwable?) {
                /*
                Error callback
                */
                retrofitEventListener.onError(call, t)
            }
        })

    }

}
