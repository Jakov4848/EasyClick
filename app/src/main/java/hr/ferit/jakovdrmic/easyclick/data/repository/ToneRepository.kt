package hr.ferit.jakovdrmic.easyclick.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.jakovdrmic.easyclick.data.model.Tone

class ToneRepository {

    private val db = FirebaseFirestore.getInstance()
    private val tonesCollection = db.collection("tones")

    fun getAllTones(onResult: (List<Tone>) -> Unit, onError:(Exception) -> Unit){
        tonesCollection
            .get()
            .addOnSuccessListener { snapshot ->
                val tones = snapshot.documents.mapNotNull{doc ->
                    doc.toObject(Tone::class.java)?.copy(id = doc.id)
                }
                onResult(tones)
            }
            .addOnFailureListener{exception ->
                onError(exception)
            }

    }
}