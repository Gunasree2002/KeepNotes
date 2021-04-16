package com.example.keepnotes

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NotesDao {

        @Insert
        fun insert(notes: Notes)

        @Query("select * from Notes")
        fun getNotes(): LiveData<List<Notes>>

        @Query("SELECT * from Notes where description = :desc")

        fun getNote(desc: String): Notes

        @Query("DELETE FROM Notes WHERE description = :desc")
        fun delete(desc: String)

        @Delete
        fun delete(notes: Notes)
        @Update
        fun update(notes: Notes)

        @Query("UPDATE Notes SET title = :tit, description= :desc WHERE id =:id")
        fun update(tit: String?, desc: String?, id: Int)

        @Insert
        fun insertIntoTrash(trashNotes: Trash)

        @Query("select * from Trash")
        fun getTrash(): LiveData<List<Trash>>

       @Query("DELETE from Trash")
       fun deleteAllFromTrash()

        @Insert
        fun inserIntoArchieve(archieve : Archieve)

        @Query("SELECT * from Archieve")
        fun getArchieve(): LiveData<List<Archieve>>

        @Query("DELETE from Archieve where a_id =:aId " )
        fun dltArchieve(aTit: String,aDesc: String,aId: Int)

//    @Delete
//    fun deleteArchieve(
//        dltArchieve: String,
//        archievedescription: String,
//        aId: Int
//    )
}