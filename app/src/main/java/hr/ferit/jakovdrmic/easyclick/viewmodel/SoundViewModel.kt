package hr.ferit.jakovdrmic.easyclick.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import hr.ferit.jakovdrmic.easyclick.data.SoundData
import hr.ferit.jakovdrmic.easyclick.data.model.Sound

class SoundViewModel : ViewModel() {

    val sounds: List<Sound> = SoundData.sounds

    private val _selectedSound = mutableStateOf<Sound?>(null)
    val selectedSound: State<Sound?> = _selectedSound

    fun selectSound(sound: Sound) {
        _selectedSound.value = sound
    }
}
