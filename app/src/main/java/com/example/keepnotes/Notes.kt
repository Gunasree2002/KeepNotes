package com.example.keepnotes

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

    @Entity
    data class Notes(
        @NonNull
    @PrimaryKey(autoGenerate = true) var id: Int =0,
        var title: String,
        var description: String

    )

    @Entity
    data class Trash(
        @NonNull
        @PrimaryKey(autoGenerate = true) var t_id: Int =0,
        var trashTitle: String,
        var trashNote: String
    )

    @Entity
    data class Archieve(
        @NonNull
        @PrimaryKey(autoGenerate = true) var a_id: Int =0,
        var archieveTitle: String,
        var archievedescription: String
    )
