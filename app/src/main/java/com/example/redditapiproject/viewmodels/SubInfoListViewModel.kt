package com.example.redditapiproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.redditapiproject.models.SubInfo
import com.example.redditapiproject.models.SubmissionListing

class SubInfoListViewModel() : ViewModel() {
    private val _submissionList = MutableLiveData<MutableList<SubInfo>>()

    private val _norefresh = MutableLiveData<Boolean>().apply { value = false }

    val submissionList: LiveData<MutableList<SubInfo>> = _submissionList

    val norefresh: LiveData<Boolean> = _norefresh

    fun setNoRefresh(nr: Boolean){
        _norefresh.postValue(nr)
    }

    fun setSubList(list: List<SubInfo>) {
        _submissionList.postValue(list.toMutableList())
    }

    fun clearSubList() {
        _submissionList.postValue(mutableListOf())
    }


}