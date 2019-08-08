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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI




class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        navController = findNavController(R.id.navHostFragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onNavigateUp()
    }

}