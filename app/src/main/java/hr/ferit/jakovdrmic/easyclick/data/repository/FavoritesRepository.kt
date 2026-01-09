package hr.ferit.jakovdrmic.easyclick.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import hr.ferit.jakovdrmic.easyclick.data.model.FavoriteTone


class FavoritesRepository {

    private val db = FirebaseFirestore.getInstance()

    fun addToFavorites(userId: String, toneId: String) {
        val favorite = FavoriteTone(toneId = toneId)

        db.collection("users")
            .document(userId)
            .collection("favorites")
            .document(toneId)
            .set(favorite)
    }

    fun removeFromFavorites(userId: String, toneId: String) {
        db.collection("users")
            .document(userId)
            .collection("favorites")
            .document(toneId)
            .delete()
    }

    fun getFavorites(userId: String, onResult: (List<FavoriteTone>) -> Unit, onError: (Exception) -> Unit) {
        db.collection("users")
            .document(userId)
            .collection("favorites")
            .get()
            .addOnSuccessListener { snapshot ->
                val favorites = snapshot.toObjects(FavoriteTone::class.java)
                onResult(favorites)
            }
            .addOnFailureListener { exception ->
                onError(exception)
            }
    }
}