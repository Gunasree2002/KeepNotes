package com.example.keepnotes

import androidx.lifecycle.LiveData
import androidx.room.*

@Database(entities = [Notes::class , Trash::class , Archieve::class],version = 1)
abstract class NotesDb : RoomDatabase() {

    abstract fun notesDao() : NotesDao
}

