package com.example.redditapiproject.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class SubmissionListing {
    @SerializedName("data")
    @Expose
    var data: SubmissionListingData? = null
}

class SubmissionListingData {
    @SerializedName("children")
    @Expose
    var children: List<Submission>? = null

    @SerializedName("next")
    @Expose
    var next: String? = null
}