package hr.ferit.jakovdrmic.easyclick.data.repository


import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import hr.ferit.jakovdrmic.easyclick.data.model.Note
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.Flow

class FirebaseNotesRepository {

    private val db = FirebaseFirestore.getInstance()
    private val collection = db.collection("notes")

    // Save a note
    fun addNote(note: Note) {
        collection.document(note.id).set(note)
    }

    // Delete a note
    fun deleteNote(noteId: String) {
        collection.document(noteId).delete()
    }

    // Observe notes as a Flow
    fun getNotesFlow(): Flow<List<Note>> = callbackFlow {
        val listener: ListenerRegistration = collection.addSnapshotListener { snapshot, error ->
            if (error != null) {
                close(error)
                return@addSnapshotListener
            }

            val notes = snapshot?.documents?.mapNotNull {
                it.toObject(Note::class.java)
            } ?: emptyList()

            trySend(notes)
        }

        awaitClose { listener.remove() }
    }
}
