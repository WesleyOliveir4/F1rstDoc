package com.example.f1rstdoc.data.room.repository

import androidx.lifecycle.LiveData
import com.example.f1rstdoc.data.room.dao.DocsDao
import com.example.f1rstdoc.domain.docs.model.Docs
import com.example.f1rstdoc.domain.docs.usecase.DocsRoomDatabaseUseCase

class DocsRoomDatabaseImpl(private val docsDao: DocsDao):DocsRoomDatabaseUseCase {
    override fun getDocs(): LiveData<List<Docs>> {
        return docsDao.getDocs()
    }

    override fun insertDocs(docs: Docs) {
        return docsDao.insertDocs(docs = docs )
    }

    override fun deleteDocs(id: Int) {
        return docsDao.deleteDocs(id = id)
    }

    override fun updateDocs(docs: Docs) {
        return docsDao.updateDocs(docs = docs)
    }
}