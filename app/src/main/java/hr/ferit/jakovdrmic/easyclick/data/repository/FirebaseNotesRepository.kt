package hr.ferit.jakovdrmic.easyclick.repository

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import hr.ferit.jakovdrmic.easyclick.data.model.Note

class FirebaseNotesRepository {

    private val db = FirebaseFirestore.getInstance() // Firestore instance
    private val collection = db.collection("notes") // "notes" collection in Firestore

    fun addNote(note: Note) {
        collection.document(note.id).set(note)
    }

    fun deleteNote(noteId: String) {
        collection.document(noteId).delete()
    }

    // Listens to real-time updates on the "notes" collection
    // Converts Firestore documents into Note objects
    // Calls onUpdate when collection changes
    // Returns ListenerRegistration to stop listening
    fun listenToNotes(onUpdate: (List<Note>) -> Unit): ListenerRegistration {
        return collection.addSnapshotListener { snapshot, _ ->
            if (snapshot != null) {
                val notes = snapshot.documents.mapNotNull { it.toObject(Note::class.java) }
                onUpdate(notes)
            }
        }
    }
}