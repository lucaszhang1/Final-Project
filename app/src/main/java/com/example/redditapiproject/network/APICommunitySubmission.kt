package com.example.redditapiproject.network

import com.example.redditapiproject.models.SubmissionListing
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * API for getting weather from https://darksky.net/
 */
interface APICommunitySubmission {
    @GET("r/{subredditName}.json")
    fun getSubmissionList(
        @Path("subredditName") subredditName: String,
        @QueryMap options: Map<String, String>
    ): Call<SubmissionListing>
}
