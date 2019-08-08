package com.example.redditapiproject.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.redditapiproject.models.SubInfo
import com.example.redditapiproject.models.SubmissionListing

class SubInfoListViewModel() : ViewModel() {
    private val _submissionList = MutableLiveData<MutableList<SubInfo>>()
    private val _subUserTable = MutableLiveData<MutableMap<String, MutableSet<String>>>()

    val submissionList: LiveData<MutableList<SubInfo>> = _submissionList
    val subUserTable: LiveData<MutableMap<String, MutableSet<String>>> = _subUserTable

    fun setSubList(list: List<SubInfo>) {
        _submissionList.postValue(list.toMutableList())
    }

    fun clearSubList() {
        _submissionList.postValue(mutableListOf())
        _subUserTable.postValue(mutableMapOf())
    }

    fun addUserToSub(user: String, sub: String) {
        val table = _subUserTable.value ?: mutableMapOf()
        val cur = table[sub];
        if (cur != null) {
            cur.add(user)
        } else {
            table[sub] = mutableSetOf(user)
        }
        _subUserTable.postValue(table)

    }
}