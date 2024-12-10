package com.darleyleal.keepmemo.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.darleyleal.keepmemo.data.KeepMemoRepository
import com.darleyleal.keepmemo.data.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NoteViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = KeepMemoRepository(application.applicationContext)

    private val _title = MutableStateFlow("")
    val title = _title.asStateFlow()

    private val _description = MutableStateFlow("")
    val description = _description.asStateFlow()

    private val _notesList = MutableStateFlow<List<Note>>(emptyList())
    val notes = _notesList.asStateFlow()

    private val _note = MutableStateFlow<Note?>(null)
    val note = _note.asStateFlow()

    private val _searchText = MutableStateFlow("")
    val searchtText = _searchText.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            repository.listAllNotes().distinctUntilChanged()
                .collect { listOfNotes ->
                    when {
                        listOfNotes.isEmpty() -> {
                            Log.d(TAG, "The list is empty!: ")
                        }

                        else -> {
                            _notesList.value = listOfNotes
                        }
                    }
                }
        }
    }

    fun updateValueTitleField(newValue: String) {
        _title.value = newValue
    }

    fun updateValueDescriptionField(newValue: String) {
        _description.value = newValue
    }

    fun updateSearchText(newValue: String) {
        _searchText.value = newValue
    }

    fun validadeFields(title: String, description: String): Boolean {
        return (title.trim().isEmpty() || description.trim().isEmpty())
    }

    fun insert(title: String, description: String) {
        viewModelScope.launch {
            repository.insert(title, description)
            cleanFieldsForm()
        }
    }

    fun delete(id: Long) {
        viewModelScope.launch {
            repository.delete(id)
            listAllNotes()
        }
    }

    fun update(id: Long, title: String, description: String) {
        viewModelScope.launch {
            repository.update(id, title, description)
            cleanFieldsForm()
        }
    }

    fun getNoteById(id: Long) {
        viewModelScope.launch {
            _note.value = repository.getNoteById(id)
        }
    }

    fun listAllNotes() {
        viewModelScope.launch {
            repository.listAllNotes().distinctUntilChanged().collect {
                _notesList.value = it
            }
        }
    }

    fun cleanFieldsForm() {
        _title.value = ""
        _description.value = ""
    }
}