package com.example.keepnotes

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toolbar
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    //@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.setTitle("KeepNotes")
        Handler().postDelayed(
            { val i = Intent(this,NotesMainScreen::class.java)
                startActivity(i)},100)

    }

}