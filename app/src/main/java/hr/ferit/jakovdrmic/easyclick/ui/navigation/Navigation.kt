package hr.ferit.jakovdrmic.easyclick.ui.navigation

import android.content.Context
import android.media.SoundPool
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hr.ferit.jakovdrmic.easyclick.ui.MetronomeScreen
import hr.ferit.jakovdrmic.easyclick.ui.NotesScreen
import hr.ferit.jakovdrmic.easyclick.ui.SoundsScreen
import hr.ferit.jakovdrmic.easyclick.viewmodel.SoundViewModel


const val METRONOME_SCREEN = "metronome_screen"
const val NOTES_SCREEN = "notes_screen"
const val SOUNDS_SCREEN = "sounds_screen"



@Composable
fun AppNavigation(soundPool: SoundPool) {
    val navController = rememberNavController()
    val context = LocalContext.current

    val soundViewModel: SoundViewModel = viewModel()

    NavHost(
        navController = navController,
        startDestination = METRONOME_SCREEN,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(METRONOME_SCREEN) {
            MetronomeScreen(
                soundPool = soundPool,
                soundViewModel = soundViewModel,
                navController = navController,
                context = context
            )
        }
        composable(NOTES_SCREEN) {
            NotesScreen(navController = navController)
        }
        composable(SOUNDS_SCREEN) {
            SoundsScreen(
                navController = navController,
                soundViewModel = soundViewModel
            )
        }

    }
}
