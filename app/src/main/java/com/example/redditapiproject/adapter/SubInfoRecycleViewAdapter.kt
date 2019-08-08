package com.example.ctdrecviewtutorial.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.redditapiproject.R
import com.example.redditapiproject.models.SubInfo
import org.w3c.dom.Text

class SubInfoRecycleViewAdapter(private val onClick:(Int) -> Unit): ListAdapter<SubInfo, NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.view_holder_note, parent, false)
        return NoteViewHolder(view, onClick)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int){
        holder.note = getItem(position)
    }

}

class NoteViewHolder(itemView: View, private val onClick: (Int) -> Unit): RecyclerView.ViewHolder(itemView){
    var note: SubInfo? = null
    set(value){
        field = value
        itemView.findViewById<TextView>(R.id.textViewVHTitle).text = note?.name
        itemView.findViewById<TextView>(R.id.textViewVHPreview).text = "hits: " + note?.hits.toString()
        itemView.findViewById<TextView>(R.id.textViewVHPreview2).text = "user counts: " + note?.userCount.toString()
        itemView.findViewById<TextView>(R.id.textViewVHPreview3).text = "click on to show details"
    }

    init {
        itemView.setOnClickListener{onClick(adapterPosition)}
    }
}

class NoteDiffCallback : DiffUtil.ItemCallback<SubInfo>(){
    override fun areItemsTheSame(oldItem: SubInfo, newItem: SubInfo): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: SubInfo, newItem: SubInfo): Boolean {
        return oldItem == newItem
    }

}