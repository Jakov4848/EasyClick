package hr.ferit.jakovdrmic.easyclick.viewmodel

import android.media.SoundPool
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MetronomeViewModel(
    private val soundPool: SoundPool,
    private val clickSoundId: Int
) : ViewModel() {

    var bpm: Int = 60
        private set

    var isPlaying: Boolean = false
        private set

    private var job: Job? = null

    fun setBpm(newBpm: Int) {
        bpm = newBpm.coerceIn(20, 500) // clamp between 20-500
    }

    fun togglePlayPause() {
        isPlaying = !isPlaying
        if (isPlaying) startClicking() else stopClicking()
    }

    private fun startClicking() {
        job = viewModelScope.launch {
            var lastClickTime = System.currentTimeMillis()
            while (isPlaying) {
                val now = System.currentTimeMillis()
                val interval = 60000L / bpm
                if (now - lastClickTime >= interval) {
                    soundPool.play(clickSoundId, 1f, 1f, 1, 0, 1f)
                    lastClickTime = now
                }
                delay(10L)
            }
        }
    }

    private fun stopClicking() {
        job?.cancel()
        job = null
    }

    override fun onCleared() {
        super.onCleared()
        stopClicking() // make sure job is cancelled when ViewModel is cleared
    }
}





