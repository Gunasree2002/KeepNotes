package com.example.keepnotes

import android.app.TimePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TimePicker

class ReminderActivity : AppCompatActivity() {
    lateinit var addReminder: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder)
        addReminder = findViewById(R.id.add_reminder)
        addReminder.setOnClickListener {
            val i = Intent(this,AddingNotes::class.java)
            startActivity(i)
        }
    }

}