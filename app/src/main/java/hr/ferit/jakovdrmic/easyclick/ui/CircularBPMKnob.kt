package hr.ferit.jakovdrmic.easyclick.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

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