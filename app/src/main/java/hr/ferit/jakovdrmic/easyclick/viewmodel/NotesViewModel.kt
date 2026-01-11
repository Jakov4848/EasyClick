package hr.ferit.jakovdrmic.easyclick.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import hr.ferit.jakovdrmic.easyclick.data.model.Note
import hr.ferit.jakovdrmic.easyclick.repository.FirebaseNotesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.*

class NotesViewModel(
    private val repository: FirebaseNotesRepository = FirebaseNotesRepository()
) : ViewModel() {

    // internal mutable state of notes
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    // read only list of notes exposed to the UI
    val notes: StateFlow<List<Note>> = _notes.asStateFlow()

    // current text input in the text field
    var text = MutableStateFlow("")

    // calls listenToNotes() in the repository to get live updates
    // updates _notes with the latest list of notes
    private var listenerRegistration = repository.listenToNotes { updatedNotes ->
        _notes.value = updatedNotes.sortedBy { it.createdAt }
    }

    // updates the text state as the user types
    fun onTextChange(newText: String) {
        text.value = newText
    }

    //
    fun addNote() {
        val noteText = text.value.trim()
        if (noteText.isNotEmpty()) {
            val note = Note(
                id = UUID.randomUUID().toString(),
                text = noteText,
                createdAt = System.currentTimeMillis()
            )
            repository.addNote(note)
            text.value = ""
        }
    }

    fun removeNote(noteId: String) {
        repository.deleteNote(noteId)
    }

    // Stops listening to Firestore when the ViewModel is destroyed
    override fun onCleared() {
        super.onCleared()
        listenerRegistration.remove() // stop listening when ViewModel is destroyed
    }
}