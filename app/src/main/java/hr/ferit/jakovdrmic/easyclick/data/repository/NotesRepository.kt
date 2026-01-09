package hr.ferit.jakovdrmic.easyclick.data.repository

import android.content.Context
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import hr.ferit.jakovdrmic.easyclick.data.model.Note

class NotesRepository(context: Context) {

    private val prefs = context.getSharedPreferences("notes_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()
    private val key = "notes_list"

    fun getNotes(): List<Note> {
        val json = prefs.getString(key, null) ?: return emptyList()
        val type = object : TypeToken<List<Note>>() {}.type
        return gson.fromJson(json, type)
    }

    fun saveNote(note: Note) {
        val currentNotes = getNotes().toMutableList()
        currentNotes.add(note)
        saveAll(currentNotes)
    }

    fun deleteNote(noteId: String) {
        val updatedList = getNotes().filterNot { it.id == noteId }
        saveAll(updatedList)
    }

    private fun saveAll(notes: List<Note>) {
        val json = gson.toJson(notes)
        prefs.edit().putString(key, json).apply()
    }
}