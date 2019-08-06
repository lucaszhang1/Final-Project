package com.example.redditapiproject.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Submission {
    @SerializedName("subreddit")
    @Expose
    var subreddit: String? = null

    @SerializedName("author")
    @Expose
    var author: String? = null
}