package com.example.f1rstdoc.data.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.f1rstdoc.data.room.dao.DocsDao
import com.example.f1rstdoc.domain.docs.model.Docs

@Database(entities = [Docs::class], version = 1, exportSchema = false)
abstract class DocsDatabase : RoomDatabase() {
    abstract fun myNotesDao(): DocsDao

    companion object
    {
        var INSTANCE: DocsDatabase?=null

        fun getDatabaseInstance(context: Context): DocsDatabase {
            val tempInstance = INSTANCE
            if (tempInstance!= null){
                return  tempInstance
            }
            synchronized(this){
                val roomDatabaseInstance =
                    Room.databaseBuilder(context, DocsDatabase::class.java,"Docs").allowMainThreadQueries().build()
                INSTANCE = roomDatabaseInstance
                return return roomDatabaseInstance
            }
        }
    }

}