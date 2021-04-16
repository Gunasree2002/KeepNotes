package com.example.keepnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*

class ArchieveScreen : AppCompatActivity() {
    lateinit var archieveRecyclerview: RecyclerView
    lateinit var database : NotesDb
    lateinit var archieve: List<Archieve>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_archieve_screen)

        this.setTitle("Archieve")
        archieveRecyclerview = findViewById(R.id.archieve_recyclerview)
        database = Room.databaseBuilder(this, NotesDb::class.java, "Notes_Details").build()
        val manager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        archieveRecyclerview.layoutManager = manager

        database.notesDao().getArchieve().observe(this,object : Observer<List<Archieve>>{
            override fun onChanged(t: List<Archieve>) {
                archieve = t
                val archieveAdapter = ArchieveRecyclerAdapter(this@ArchieveScreen,archieve)
                archieveRecyclerview.adapter = archieveAdapter

            }

        })
    }
}