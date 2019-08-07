package com.example.ctdrecviewtutorial.adapter

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.redditapiproject.R

class SubInfoRecycleViewAdapter(private val onClick:(Int) -> Unit): ListAdapter<SubInfoRecycleViewAdapter, NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_note, parent, false)
        return NoteViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int){
        holder.note = getItem(position)
    }

}

class NoteViewHolder(itemView: View, private val onClick: (Int) -> Unit): RecyclerView.ViewHolder(itemView){
    var note: SubInfoRecycleViewAdapter? = null
    set(value){
        field = value
        itemView.findViewById<TextView>(R.id.textViewVHTitle).text = note?.title
        itemView.findViewById<TextView>(R.id.textViewVHPreview).text = note?.content
        itemView.findViewById<TextView>(R.id.textViewVHDate).text = note?.timeStamp.toString()
    }

    init {
        itemView.setOnClickListener{onClick(adapterPosition)}
    }
}

class NoteDiffCallback : DiffUtil.ItemCallback<Note>(){
    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }

}