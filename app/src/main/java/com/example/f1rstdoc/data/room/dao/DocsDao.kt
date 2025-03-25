package com.example.f1rstdoc.data.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.f1rstdoc.domain.docs.model.Docs
import kotlinx.coroutines.flow.Flow

@Dao
interface DocsDao {

    @Query("SELECT * FROM Docs WHERE idUser=:idUser")
    fun getDocs(idUser:String):Flow<List<Docs>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDocs(docs: Docs)

    @Query("DELETE FROM Docs WHERE id=:id")
    fun deleteDocs(id:Int)

    @Update
    fun updateDocs(docs: Docs)
}