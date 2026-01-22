package hr.ferit.jakovdrmic.easyclick.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import hr.ferit.jakovdrmic.easyclick.data.SoundData
import hr.ferit.jakovdrmic.easyclick.data.model.Sound

class SoundViewModel : ViewModel() {

    val sounds = SoundData.sounds

    private val _selectedSound = mutableStateOf(sounds.first())
    val selectedSound: State<Sound?> = _selectedSound

    private val _favorites = mutableStateListOf<String>()
    val favoriteSounds: List<Sound>get() = sounds.filter { _favorites.contains(it.id) }
    fun selectSound(sound: Sound) {
        _selectedSound.value = sound
    }

    fun toggleFavorite(sound:Sound){
        if(_favorites.contains(sound.id)){
            _favorites.remove(sound.id)
        }
        else{
            _favorites.add(sound.id)

        }
    }

    fun isFavorite(sound:Sound):Boolean=
        _favorites.contains(sound.id)

}
