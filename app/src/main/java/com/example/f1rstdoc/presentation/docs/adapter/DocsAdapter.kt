package com.example.f1rstdoc.presentation.docs.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.f1rstdoc.databinding.ItemDocsBinding
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.presentation.docs.home.HomeFragmentDirections

class DocsAdapter(private val docsList: List<Docs>) :
    RecyclerView.Adapter<DocsAdapter.DocsViewHolder>() {

    class DocsViewHolder(val binding: ItemDocsBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DocsViewHolder {
        return DocsViewHolder(
            ItemDocsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DocsViewHolder, position: Int) {
        val data = docsList[position]
        holder.binding.docTitle.text = docsList[position].title
        holder.binding.docSubTitle.text = docsList[position].subTitle
        holder.binding.docData.text = data.date

        holder.binding.root.setOnClickListener{
            val action=HomeFragmentDirections.actionHomeFragmentToEditDocsFragment(data)
            Navigation.findNavController(it).navigate(action)


        }

    }

    override fun getItemCount() = docsList.size
}