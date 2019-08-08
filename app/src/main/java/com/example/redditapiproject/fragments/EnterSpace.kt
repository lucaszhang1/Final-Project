package com.example.redditapiproject.fragments


import android.app.Activity
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController

import com.example.redditapiproject.R
import kotlinx.android.synthetic.main.fragment_enter_face.*

/**
 * A simple [Fragment] subclass.
 *
 */
class EnterSpace : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_enter_face, container, false)



        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        button.setOnClickListener {

            val imm = context?.getSystemService(Activity.INPUT_METHOD_SERVICE)!! as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)

            findNavController().navigate(R.id.action_enterSpace_to_outcome, bundleOf(
                getString(R.string.subreddit_name) to editText.text.toString()
            ))
        }
    }


}