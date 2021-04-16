package com.example.keepnotes

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.view.View
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewHolder(itemView: View, var context: Context) : RecyclerView.ViewHolder(
    itemView
) {
    fun setView(allNotes: List<Notes>, position: Int) {

        val card : CardView = itemView.findViewById(R.id.card_view)
        val displayTitle: TextView = itemView.findViewById(R.id.display_title)
        val displayDescription : TextView= itemView.findViewById(R.id.display_description)

        val eachNote = allNotes.get(position)
        displayTitle.setText(eachNote.title)
        displayDescription.setText(eachNote.description)
        val array = arrayOf("#a7cbf8","#fbaca9","#fff17a","#9afde9","#ffb82a","#8ea883")
        if (position < 6){
            card.setCardBackgroundColor(Color.parseColor(array[position]))
        }
        else{
            card.setCardBackgroundColor(Color.parseColor(array[position%6]))
        }

        card.setOnClickListener {
            val i =Intent(context,ParticularNote::class.java)
            i.putExtra("particularPosition",position)
            i.putExtra("desc", eachNote.description)
            context.startActivity(i)
        }
    }

}
