package com.example.notesapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.databinding.NoteItemBinding
import com.example.notesapp.models.note

class NotesAdapter(
    private var notes: List<note>,
    private val onNoteClick: (note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    inner class NoteViewHolder(
        val binding: NoteItemBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {

        val binding = NoteItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {

        val note = notes[position]

        holder.binding.txtNoteTitle.text = note.title
        holder.binding.txtNoteSubtitle.text = note.subtitle
        holder.binding.root.setOnClickListener {
            onNoteClick(note)
        }
    }

    override fun getItemCount(): Int {

        android.util.Log.d("ADAPTER", "Items = ${notes.size}")

        return notes.size
    }

    fun updateNotes(newNotes: List<note>) {
        notes = newNotes
        notifyDataSetChanged()
    }

    fun filterList(filteredList: List<note>) {

        notes = filteredList

        notifyDataSetChanged()

    }
}