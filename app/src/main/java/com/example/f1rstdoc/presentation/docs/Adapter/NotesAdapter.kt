package com.example.devk.presentation.ui.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.f1rstdoc.databinding.ItemNotesBinding
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.presentation.docs.home.HomeFragmentDirections

class NotesAdapter(private val notesList: List<Docs>) :
    RecyclerView.Adapter<NotesAdapter.NotesViewHolder>() {

    class NotesViewHolder(val binding: ItemNotesBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotesViewHolder {
        return NotesViewHolder(
            ItemNotesBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: NotesViewHolder, position: Int) {
        val data = notesList[position]
        holder.binding.notesTitle.text = notesList[position].title
        holder.binding.notesSubTitle.text = notesList[position].subTitle
        holder.binding.notesData.text = data.date

        holder.binding.root.setOnClickListener{
            val action=HomeFragmentDirections.actionHomeFragmentToEditDocsFragment(data)
            Navigation.findNavController(it).navigate(action)


        }

    }

    override fun getItemCount() = notesList.size
}