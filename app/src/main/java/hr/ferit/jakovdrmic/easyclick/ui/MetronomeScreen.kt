package hr.ferit.jakovdrmic.easyclick.ui

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import android.media.SoundPool
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hr.ferit.jakovdrmic.easyclick.viewmodel.MetronomeViewModel
import hr.ferit.jakovdrmic.easyclick.viewmodel.SoundViewModel



@Composable
fun MetronomeScreen(
    soundPool: SoundPool,
    soundViewModel: SoundViewModel = viewModel(),
    navController: NavController,
    context: Context
) {
    val viewModel: MetronomeViewModel = viewModel()

    val bpm = viewModel.bpm
    val isPlaying = viewModel.isPlaying

    // runs once when the screen appears
    LaunchedEffect(Unit){

        viewModel.init(soundPool,soundViewModel, context)
    }

    // runs when leaving the screen
    DisposableEffect(Unit) {
        onDispose {
            viewModel.forceStop()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x7393B3F0))
    ) {

        IconButton(
            onClick = { navController.navigate("notes_screen") },
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

        IconButton(
            onClick = { navController.navigate("sounds_screen") },
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
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

            Text(
                text = "$bpm BPM",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(32.dp))

            CircularBPMKnob(
                bpm = bpm,
                onBpmChange = { viewModel.onBpmChange(it) },
                isPlaying = isPlaying,
                onPlayPauseToggle = { viewModel.togglePlayPause() },
                minBpm = 20,
                maxBpm = 300
            )
        }
    }
}
