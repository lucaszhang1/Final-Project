package com.example.redditapiproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.redditapiproject.models.SubInfo
import com.example.redditapiproject.models.SubmissionListing

class SubInfoListViewModel() : ViewModel() {
    private val _submissionList = MutableLiveData<MutableList<SubInfo>>()

    val submissionList: LiveData<MutableList<SubInfo>> = _submissionList

    fun addSubmission(sub: SubInfo) {
        val list = _submissionList.value
        list?.add(sub)
        _submissionList.postValue(list ?: mutableListOf(sub))
    }


}