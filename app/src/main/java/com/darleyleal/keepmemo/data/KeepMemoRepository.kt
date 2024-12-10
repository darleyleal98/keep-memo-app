package com.darleyleal.keepmemo.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class KeepMemoRepository(context: Context) {

    private val dao: KeepMemoDatabaseDao = KeepMemoDatabase.getDatabase(context).keepMemoDAO()

    suspend fun insert(title: String, description: String) {
        dao.insert(Note(title = title, description = description))
    }

    suspend fun update(id: Long, title: String, description: String) {
        dao.update(id, title, description)
    }

    fun listAllNotes(): Flow<List<Note>> {
        return dao.getNotes().flowOn(Dispatchers.IO).conflate()
    }

    suspend fun delete(id: Long) {
        dao.delete(id)
    }

    suspend fun getNoteById(id: Long): Note {
        return dao.getNoteById(id)
    }
}