package com.example.keepnotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room

class NotesMainScreen : AppCompatActivity() {

    lateinit var addNotes: ImageView
    lateinit var recyclerview: RecyclerView
    lateinit var database: NotesDb
    lateinit var allNotes: List<Notes>
    lateinit var moreOption: ImageView
    lateinit var menuLayout: FrameLayout
    lateinit var searchBar: CardView
    lateinit var adapter: RecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_notes)
        this.setTitle("Keep Notes")
        recyclerview = findViewById(R.id.notes_recyclerview)
        moreOption = findViewById(R.id.more_option)
        val manager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        recyclerview.layoutManager = manager
        addNotes = findViewById(R.id.add_user)
        database = Room.databaseBuilder(this, NotesDb::class.java, "Notes_Details").build()

        println("oncreate caaalllllllllledddddddddd")
        addNotes.setOnClickListener {
            val i = Intent(this, AddingNotes::class.java)
            startActivity(i)
        }

        moreOption.setOnClickListener {
            println("more option clicked")
            supportFragmentManager.beginTransaction()
                .add(R.id.root_view,MenuOptions())
                .addToBackStack("back")
                .commit()
        }



    }
    override fun onResume() {
        println("resumedddddddddd!!!!!!!!!!!!")
        database.notesDao().getNotes().observe(this,
            object : Observer<List<Notes>>{
                override fun onChanged(t: List<Notes>?) {
                    t?.let{
                        allNotes = it
                        setAdapter()
                    }
                }
            })
        super.onResume()
    }

        private fun setAdapter() {
            if(::adapter.isInitialized) {
                adapter.allNotes = allNotes
                adapter.notifyDataSetChanged()
                return
            }
            adapter = RecyclerAdapter(this,allNotes)
            recyclerview.adapter = adapter
        }

    }


