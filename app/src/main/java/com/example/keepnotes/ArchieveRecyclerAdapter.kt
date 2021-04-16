package com.example.keepnotes

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar

class ArchieveRecyclerAdapter(var context: Context,var archieve: List<Archieve>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view = LayoutInflater.from(context).inflate(R.layout.noteitem_model,parent,false)
            val recyclerViewHolder = ArchieveRecyclerViewHolder(view,context)
            return recyclerViewHolder
        }

        override fun getItemCount(): Int {
            return archieve.size
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            (holder as ArchieveRecyclerViewHolder).setView(archieve,position)
        }
    }

    class ArchieveRecyclerViewHolder(itemview: View, var context: Context): RecyclerView.ViewHolder(itemview) {

        fun setView(archieve: List<Archieve>, position: Int) {

            val card: CardView = itemView.findViewById(R.id.card_view)
            val displayTitle: TextView = itemView.findViewById(R.id.display_title)
            val displayDescription: TextView = itemView.findViewById(R.id.display_description)


            val eachNote = archieve.get(position)
            displayTitle.setText(eachNote.archieveTitle)
            displayDescription.setText(eachNote.archievedescription)
            val database = Room.databaseBuilder(context, NotesDb::class.java, "Notes_Details").build()
            database.notesDao().dltArchieve(eachNote.archieveTitle,eachNote.archievedescription,eachNote.a_id)
            val array = arrayOf("#a7cbf8", "#fbaca9", "#fff17a", "#9afde9", "#ffb82a", "#8ea883")
            if (position < 6) {
                card.setCardBackgroundColor(Color.parseColor(array[position]))
            } else {
                card.setCardBackgroundColor(Color.parseColor(array[position % 6]))
            }
            itemView.setOnLongClickListener  {
                Snackbar.make(it,"Click OK to Unaechieve",Snackbar.LENGTH_LONG)
                    .setAction("Ok",View.OnClickListener {
                        Toast.makeText(context,"UnArchieve",Toast.LENGTH_LONG).show()
                        database.notesDao().dltArchieve(eachNote.archieveTitle,eachNote.archievedescription,eachNote.a_id)
                        val note = Notes(0,eachNote.archieveTitle,eachNote.archievedescription)
                        database.notesDao().insert(note)
                    })
                    .setActionTextColor(Color.GREEN)
                    .show()
              true

            }

        }

    }
