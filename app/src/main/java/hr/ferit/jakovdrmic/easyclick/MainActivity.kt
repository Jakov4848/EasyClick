package hr.ferit.jakovdrmic.easyclick

import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hr.ferit.jakovdrmic.easyclick.ui.theme.EasyClickTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.*

class MainActivity : ComponentActivity() {


    private lateinit var soundPool: SoundPool   //  used to play short sounds
    private var clickSoundId: Int = 0    // id of a click sound

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()


        soundPool = SoundPool.Builder().setMaxStreams(1).build()    // initialized soundPool(only 1 sound at a time)
        clickSoundId = soundPool.load(this, R.raw.click, 1)

        setContent {
            EasyClickTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MetronomeScreen()
                }
            }
        }
    }

    @Composable
    fun MetronomeScreen() {
        var bpm by remember { mutableStateOf(60) } // starting BPM
        var isPlaying by remember { mutableStateOf(false) }
        val coroutineScope = rememberCoroutineScope() // allows running the clicking sound asynchronosly
        var job by remember { mutableStateOf<Job?>(null) }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Number of BPM
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
                        // Start clicking
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
    @Composable
    fun CircularBPMKnob(
        bpm: Int,
        onBpmChange: (Int) -> Unit,
        isPlaying: Boolean,
        onPlayPauseToggle: () -> Unit,
        minBpm: Int = 20,
        maxBpm: Int = 500
    ) {
        var rotation by remember { mutableStateOf((bpm - minBpm).toFloat() / (maxBpm - minBpm) * 360f) }

        Box(
            modifier = Modifier
                .size(250.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, _ ->
                        val center = Offset(size.width / 2f, size.height / 2f)
                        val touch = change.position
                        val angle = atan2(
                            y = touch.y - center.y,
                            x = touch.x - center.x
                        ) * 180f / PI.toFloat() + 90f

                        // Convert angle to 0..360
                        var newRotation = angle
                        if (newRotation < 0f) newRotation += 360f

                        // Convert to BPM and clamp
                        val newBpm = (minBpm + (newRotation / 360f * (maxBpm - minBpm))).roundToInt()
                            .coerceIn(minBpm, maxBpm)

                        rotation = ((newBpm - minBpm).toFloat() / (maxBpm - minBpm) * 360f)
                        onBpmChange(newBpm)
                    }
                }
        ) {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val radius = size.minDimension / 2
                val strokeWidth = 20f
                val center = Offset(size.width / 2, size.height / 2)

                // Background circle
                drawArc(
                    color = Color.LightGray,
                    startAngle = 0f,
                    sweepAngle = 360f,
                    useCenter = false,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
                )

                // Progress arc
                drawArc(
                    color = Color(0xFF6200EE),
                    startAngle = -90f,
                    sweepAngle = rotation,
                    useCenter = false,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(width = strokeWidth)
                )

                // Handle
                val handleAngle = Math.toRadians((rotation - 90f).toDouble())
                val handleX = center.x + cos(handleAngle) * radius
                val handleY = center.y + sin(handleAngle) * radius
                drawCircle(
                    color = Color.Red,
                    radius = 15f,
                    center = Offset(handleX.toFloat(), handleY.toFloat())
                )
            }

            // Play/Pause button in center
            IconButton(
                onClick = onPlayPauseToggle,
                modifier = Modifier.align(androidx.compose.ui.Alignment.Center)
            ) {
                if (isPlaying) {
                    Icon(
                        imageVector = Icons.Default.Pause,
                        contentDescription = "Pause",
                        tint = Color.Black,
                        modifier = Modifier.size(48.dp)
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.Black,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
        }
    }


    // Releases SoundPool resources when the activity is destroyed to prevent memeory leaks.
    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}
