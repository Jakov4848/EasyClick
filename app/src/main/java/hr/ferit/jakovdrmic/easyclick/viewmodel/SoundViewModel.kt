package hr.ferit.jakovdrmic.easyclick.viewmodel

import android.app.Application
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import hr.ferit.jakovdrmic.easyclick.data.SoundData
import hr.ferit.jakovdrmic.easyclick.data.model.Sound
import hr.ferit.jakovdrmic.easyclick.data.preferences.SoundPreferences

class SoundViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>()
    val sounds = SoundData.sounds

    private val _selectedSound = mutableStateOf(
        SoundPreferences.loadSelectedSound(context)
            ?.let { id -> sounds.find { it.id == id } }
            ?: sounds.first()
    )
    val selectedSound: State<Sound?> = _selectedSound

    private val _favorites = mutableStateListOf<String>().apply {
        addAll(SoundPreferences.loadFavorites(context))
    }
    val favoriteSounds: List<Sound>get() = sounds.filter { _favorites.contains(it.id) }
    fun selectSound(sound: Sound) {
        _selectedSound.value = sound
        SoundPreferences.saveSelectedSound(context, sound.id)
    }

    fun toggleFavorite(sound:Sound){
        if(_favorites.contains(sound.id)){
            _favorites.remove(sound.id)
        }
        else{
            _favorites.add(sound.id)
        }
        SoundPreferences.saveFavorites(context, _favorites.toSet())
    }

    fun isFavorite(sound:Sound):Boolean=
        _favorites.contains(sound.id)

}
