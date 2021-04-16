package com.example.keepnotes

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout


class MenuOptions : Fragment() {
    lateinit var reminderScreen: LinearLayout
    lateinit var archieveScreen: LinearLayout
    lateinit var trashScreen : LinearLayout
    lateinit var menuLayout: FrameLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_menu_options, container, false)
        reminderScreen = view.findViewById(R.id.reminder_screen)
        trashScreen = view.findViewById(R.id.trash_screen)
        archieveScreen = view.findViewById(R.id.archieve_screen)
        menuLayout = view.findViewById(R.id.menu_layout)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        trashScreen.setOnClickListener {
            val i = Intent(context,TrashScreen::class.java)
            startActivity(i)
            menuLayout.visibility = View.GONE
        }
        reminderScreen.setOnClickListener {
            val intent = Intent(context,ReminderActivity::class.java)
            startActivity(intent)
            menuLayout.visibility = View.GONE
        }
        archieveScreen.setOnClickListener {
            val i= Intent(context,ArchieveScreen::class.java)
            startActivity(i)
            menuLayout.visibility = View.GONE
        }
    }

}