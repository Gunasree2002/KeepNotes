package com.example.keepnotes

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

    class TrashRecyclerAdapter(var context: Context, var allTrash: List<Trash>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

            val view = LayoutInflater.from(context).inflate(R.layout.noteitem_model, parent, false)
            val recyclerViewHolder =TrashRecyclerViewHolder(view,context)
            return recyclerViewHolder
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as TrashRecyclerViewHolder).setView(allTrash,position)
        }

        override fun getItemCount(): Int {
            return  allTrash.size
        }
    }