package hr.ferit.jakovdrmic.easyclick.viewmodel

import android.media.SoundPool
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MetronomeViewModelFactory(
    private val soundPool: SoundPool,
    private val clickSoundId: Int
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MetronomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MetronomeViewModel(soundPool, clickSoundId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
