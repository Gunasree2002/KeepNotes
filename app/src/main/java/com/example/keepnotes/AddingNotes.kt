package com.example.keepnotes

import android.app.AlarmManager
import android.app.Notification
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageView
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.room.Room.databaseBuilder
import com.google.firebase.database.FirebaseDatabase
import java.util.*
import kotlin.collections.HashMap
import kotlin.properties.Delegates
import com.example.keepnotes.R


class AddingNotes: AppCompatActivity(),TimePickerDialog.OnTimeSetListener
{

    lateinit var allNotes: List<Notes>
    lateinit var title: EditText
    lateinit var description: EditText
    lateinit var database:NotesDb
    lateinit var saveNote : ImageView
    lateinit var notification: ImageView
    lateinit var noteTitle: String
    lateinit var noteDescription: String
    var timeInMilli by Delegates.notNull<Long>()
    lateinit var NOTIFICATION_CHANNEL_ID : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_adding_notes2)

        notification = findViewById(R.id.notification)
        saveNote = findViewById(R.id.save_note)
        title = findViewById(R.id.note_title)
        description = findViewById(R.id.note_description)
        NOTIFICATION_CHANNEL_ID = "10001"

        database = databaseBuilder(this, NotesDb::class.java, "Notes_Details").build()
        saveNote.setOnClickListener {
            Thread {
                try {

                     noteTitle = (title.text).toString()
                     noteDescription = (description.text).toString()
                    Log.e("title", noteTitle)
                    Log.e("des", noteDescription)
                  if (noteDescription.isNotEmpty()){

                      val notes = Notes(0, noteTitle, noteDescription)
                      println(notes)
                      database.notesDao().insert(notes)
                      val i = Intent(this, NotesMainScreen::class.java)
                      startActivity(i)
                  }
                   else {
                      runOnUiThread {
                          Toast.makeText(this,"Please enter description",Toast.LENGTH_LONG).show()
                      }

                   }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }.start()
        }

        notification.setOnClickListener {
            noteTitle = (title.text).toString()
            noteDescription = (description.text).toString()
            val c = Calendar.getInstance()
            val hour = c[Calendar.HOUR_OF_DAY]
            val minute = c[Calendar.MINUTE]
            val timePickerDialog = TimePickerDialog(this, this, hour, minute, false)
            timePickerDialog.show()


        }

    }

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
       println(hourOfDay.toString() + ":" + minute)

       val  cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
        cal.set(Calendar.MINUTE, minute);
        cal.set(Calendar.SECOND, 0)
        timeInMilli = cal.timeInMillis
        val hashMap = HashMap<String,String>()
        hashMap.put(noteTitle,noteDescription)
        //val database = FirebaseDatabase.getInstance("https://keep-notes-888-default-rtdb.firebaseio.com/")
        //val myREf = database.reference.child("reminder").push()
        //myREf.setValue(hashMap)
        getNotification(noteDescription)?.let { scheduleNotification(it, timeInMilli) }
    }
    private fun scheduleNotification(notification: Notification, delay: Long) {
        val notificationIntent = Intent(this, NotificationBroadcast::class.java)
        notificationIntent.putExtra("notification-id", 1)
        notificationIntent.putExtra("notification", notification)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val alarmManager =
            getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.set(AlarmManager.RTC_WAKEUP,timeInMilli,pendingIntent)

    }

    private fun getNotification(content: String): Notification? {
        val builder: NotificationCompat.Builder = NotificationCompat.Builder(this,"default")
        builder.setContentTitle(noteTitle)
        builder.setContentText(content)
        builder.setSmallIcon(R.drawable.ic_launcher_background)
        builder.setAutoCancel(true)
        builder.setChannelId(NOTIFICATION_CHANNEL_ID)
        return builder.build()
    }
}

