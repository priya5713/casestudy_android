package com.casestudy.demo.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.casestudy.demo.R
import com.casestudy.demo.model.Note
import com.casestudy.demo.util.AppUtils
import com.casestudy.demo.util.NoteDiffUtil

class NotesListAdapter(private val notes: MutableList<Note>) : RecyclerView.Adapter<NotesListAdapter.CustomViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_list_item, null)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        val note = getItem(position)

        holder.itemTitle.text = note.title
        holder.itemTime.text = note.createdAt?.let { AppUtils.getFormattedDateString(it) }

        if (note.isEncrypt) {
            holder.itemTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_lock, 0)

        } else {
            holder.itemTime.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, 0)
        }
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    fun getItem(position: Int): Note {
        return notes[position]
    }

    fun addTasks(newNotes: List<Note>) {
        val noteDiffUtil = NoteDiffUtil(notes, newNotes)
        val diffResult = DiffUtil.calculateDiff(noteDiffUtil)
        notes.clear()
        notes.addAll(newNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val itemTitle: TextView = itemView.findViewById(R.id.item_title)
        val itemTime: TextView = itemView.findViewById(R.id.item_desc)

    }
}
