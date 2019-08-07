package com.example.redditapiproject.network

import com.example.redditapiproject.models.SubmissionListing
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

interface APIUserSubmission {
    @GET("user/{username}/overview.json")
    fun getSubmissionList(
        @Path("username") username: String,
        @QueryMap options: Map<String, String>
    ): Call<SubmissionListing>
}