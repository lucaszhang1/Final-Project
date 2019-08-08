package com.example.redditapiproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.redditapiproject.R
import com.example.redditapiproject.models.SubInfo
import com.example.redditapiproject.viewmodels.SubInfoListViewModel
import org.w3c.dom.Text

class UserListRecycleViewAdapter: ListAdapter<String, UserViewHolder>(UserDiffCallback()) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_username, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.name = getItem(position)
    }

}

class UserViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    var name: String? = null
        set(value) {
            field = value
            itemView.findViewById<TextView>(R.id.usernameText).text = "u/${value}"
        }

}

class UserDiffCallback : DiffUtil.ItemCallback<String>(){
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

}