package com.example.keepnotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room.databaseBuilder

class TrashScreen : AppCompatActivity() {
    lateinit var trashRecyclerview: RecyclerView
    lateinit var database: NotesDb
    lateinit var allTrash: List<Trash>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setTitle("Trash")
        setContentView(R.layout.activity_trash_screen)
        trashRecyclerview = findViewById(R.id.trash_recyclerview)
        database = databaseBuilder(this,NotesDb::class.java,"Notes_Details").build()
        val manager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        trashRecyclerview.layoutManager = manager

        database.notesDao().getTrash().observe(this,object : Observer<List<Trash>> {
            override fun onChanged(t: List<Trash>) {
                allTrash = t
                val notesAdapter = TrashRecyclerAdapter(this@TrashScreen,allTrash)
                trashRecyclerview.adapter = notesAdapter
            }
        }
        )

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.trashmenu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.deleteAll) {
            Thread{
                database.notesDao().deleteAllFromTrash()
            }.start()
        }
        return true
    }
}