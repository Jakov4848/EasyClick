package hr.ferit.jakovdrmic.easyclick.viewmodel

import android.media.SoundPool
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MetronomeViewModel : ViewModel() {
    private var soundPool: SoundPool? = null
    private var clickSoundId: Int = 0
    fun init(soundPool: SoundPool, clickSoundId: Int) {
        this.soundPool = soundPool
        this.clickSoundId = clickSoundId
    }

    var bpm by mutableStateOf(60)
        private set

    var isPlaying by mutableStateOf(false)
        private set

    // coroutine that run metronome loop
    private var job: Job? = null

    fun onBpmChange(newBpm: Int) {
        bpm = newBpm
    }

    fun togglePlayPause() {
        isPlaying = !isPlaying

        if (isPlaying) {
            startClicking()
        } else {
            stopClicking()
        }
    }

    private fun startClicking() {
        job = viewModelScope.launch {
            while (isPlaying) {
                val interval = 60000L / bpm // milliseconds between clicks
                soundPool?.play(clickSoundId, 1f, 1f, 1, 0, 1f)
                delay(interval)
            }
        }
    }

    private fun stopClicking() {
        job?.cancel()
        job = null
    }

    fun forceStop() {
        isPlaying = false
        stopClicking()
    }

    override fun onCleared() {
        super.onCleared()
        stopClicking()
    }

}





