package hr.ferit.jakovdrmic.easyclick.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import hr.ferit.jakovdrmic.easyclick.data.model.Note

class NotesViewModel: ViewModel() {
    var notes by mutableStateOf(listOf<Note>())
        private set

    var text by mutableStateOf("")
        private set

    fun onTextChange(newText:String){
        text = newText
    }

    fun addNote(){
        if (text.isNotBlank()){
            notes = notes + Note(text = text)
            text = ""
        }
    }
}