package hr.ferit.jakovdrmic.easyclick.viewmodel

import android.content.Context
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
    private var clickSoundId: Int? = null
    private var soundViewModel: SoundViewModel? = null
    private val loadedSounds = mutableMapOf<Int, Int>()

    fun init(soundPool: SoundPool, soundViewModel: SoundViewModel, context: Context) {
        this.soundPool = soundPool
        this.soundViewModel = soundViewModel

        // preload all sounds into SoundPool
        soundViewModel.sounds.forEach { sound->
            val soundPoolId = soundPool.load(context, sound.resId, 1)
            loadedSounds[sound.resId] = soundPoolId
        }

        // set initial clickSoundId
        clickSoundId = loadedSounds[soundViewModel.selectedSound.value?.resId]
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

                val selectedResId = soundViewModel?.selectedSound?.value?.resId
                clickSoundId = selectedResId?.let{loadedSounds[it]}

                clickSoundId?.let{soundPool?.play(it, 1f, 1f, 1, 0, 1f)}

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
