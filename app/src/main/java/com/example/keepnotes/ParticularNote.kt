package com.example.keepnotes

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.room.Room.databaseBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.concurrent.thread
import kotlin.properties.Delegates

class ParticularNote : AppCompatActivity() {
    lateinit var particularnote: EditText
    lateinit var allNotes: List<Notes>
    lateinit var database: NotesDb
    lateinit var desc: String
    lateinit var currentNote: Notes
    //lateinit var editTit: EditText
    //lateinit var eachParticularItem : String
    lateinit var allTrash: List<Trash>

    lateinit var particulatItem: LinearLayout
    var itemPsition by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_particular_note)
       // editTit = findViewById(R.id.editTitle) as EditText
//        toolbar.setTitle(editTit.text)
//        this.setTitle("")
        database = databaseBuilder(this, NotesDb::class.java, "Notes_Details").build()

        itemPsition = intent.extras!!.getInt("particularPosition")
        desc = intent.extras!!.getString("desc")!!
        particulatItem = findViewById(R.id.particular_Item)
        particularnote = findViewById(R.id.particular_note)

        Log.e("position", itemPsition.toString())


        database.notesDao().getNotes().observe(this, object : Observer<List<Notes>> {
            override fun onChanged(t: List<Notes>) {
                allNotes = t
                //setfNote()
            }
        }
        )
        Thread {
            val note = database.notesDao().getNote(desc)
            println(note)
            runOnUiThread {
                setNote(note)
            }
        }.start()


    }

//    private fun setfNote() {
//        val array = arrayOf("#a7cbf8", "#fbaca9", "#fff17a", "#9afde9", "#ffb82a", "#8ea883")
//        if (itemPsition < 6) {
//            particulatItem.setBackgroundColor(Color.parseColor(array[itemPsition]))
//        } else {
//            particulatItem.setBackgroundColor(Color.parseColor(array[itemPsition % 6]))
//        }
//        val eachParticularItem = allNotes.get(itemPsition)
//        println(eachParticularItem.title)
//        this.setTitle(eachParticularItem.title)
//        particularnote.setText(eachParticularItem.description)
//    }

    private fun setNote(note: Notes) {
        currentNote = note
        this.setTitle(note.title)
        particularnote.setText(note.description)
    }

    override fun onBackPressed() {

        val changedNotes = Notes(0,currentNote.title, (particularnote.text).toString())

        AlertDialog.Builder(this)
            .setTitle("Click save to save the changes")
            .setMessage("Click cancel to discard")
            .setPositiveButton("Save",
                DialogInterface.OnClickListener { dialog, which ->
                    Thread {
                        try {
                            Log.e("tit",currentNote.title)
                            Log.e("desc",particularnote.text.toString())
                            //val changedNote = Notes(0,currentNote.title,particularnote.text.toString())
                            Log.e("changed note", changedNotes.toString())
                            database.notesDao().update(currentNote.title,particularnote.text.toString(),currentNote.id)
                            //database.notesDao().update(changedNotes)

                        } catch (e: Exception) {
                            e.printStackTrace()
                        }
                    }.start()
                    super.onBackPressed()
                })
            .setNegativeButton("Cancel",
                DialogInterface.OnClickListener { dialog, which ->
                    super.onBackPressed()
                })
            .show()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            Toast.makeText(
                this,
                "trash selected",
                Toast.LENGTH_SHORT
            ).show()
            Thread {
                try {
//                    println(allNotes.get(itemPsition))
//                    println(allNotes[itemPsition].title)
//                    val trashNote = Trash(
//                        allNotes.get(itemPsition).title,
//                        allNotes.get(itemPsition).description
//                    )
//                    database.notesDao().insertIntoTrash(trashNote)
////                    val i = Intent(this, NotesMainScreen::class.java)
////                    startActivity(i)
//                    database.notesDao().delete(allNotes.get(itemPsition))

                    val trashNote = Trash(0,currentNote.title, currentNote.description)
                    database.notesDao().insertIntoTrash(trashNote)
                    database.notesDao().delete(currentNote.description)
                    print("")

                } catch (e: Exception) {
                    Log.e("Error", e.message!!)
                    e.printStackTrace()
                }
            }.start()
            this.finish()

        } else if (item.itemId == R.id.archieve) {
            Toast.makeText(
                this,
                "Archieve selcted",
                Toast.LENGTH_SHORT
            ).show()
            Thread {
                try {
                    val archieveNote = Archieve(0,currentNote.title,currentNote.description)
                    database.notesDao().inserIntoArchieve(archieveNote)
                    database.notesDao().delete(currentNote.description)
                }
                catch (e: java.lang.Exception){
                    e.printStackTrace()
                }
            }.start()
            this.finish()
        }
        return true
    }

}

