package hr.ferit.jakovdrmic.easyclick.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import android.media.SoundPool
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.navigation.NavController


@Composable
fun MetronomeScreen(
    soundPool: SoundPool,
    clickSoundId: Int,
    navController: NavController
) {
    var bpm by remember { mutableStateOf(60) }
    var isPlaying by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()
    var job by remember { mutableStateOf<Job?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {

        // Notes icon (navigate to Notes)
        IconButton(
            onClick = {navController.navigate("notes_screen")},
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MenuBook,
                contentDescription = "Notes",
                modifier = Modifier.size(28.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Display BPM
            Text(
                text = "$bpm BPM",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Circular BPM knob
            CircularBPMKnob(
                bpm = bpm,
                onBpmChange = { bpm = it },
                isPlaying = isPlaying,
                onPlayPauseToggle = {
                    isPlaying = !isPlaying
                    if (isPlaying) {
                        // Start clicking asynchronously
                        job = coroutineScope.launch {
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
                    } else {
                        job?.cancel()
                    }
                },
                minBpm = 20,
                maxBpm = 500
            )
        }
    }
}
