package hr.ferit.jakovdrmic.easyclick.ui.navigation

import android.media.SoundPool
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import hr.ferit.jakovdrmic.easyclick.ui.MetronomeScreen
import hr.ferit.jakovdrmic.easyclick.ui.NotesScreen

const val METRONOME_SCREEN = "metronome_screen"
const val NOTES_SCREEN = "notes_screen"

@Composable
fun AppNavigation(soundPool: SoundPool, clickSoundId: Int) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = METRONOME_SCREEN,
        modifier = Modifier.fillMaxSize()
    ) {
        composable(METRONOME_SCREEN) {
            MetronomeScreen(
                soundPool = soundPool,
                clickSoundId = clickSoundId,
                onNavigateToNotes = { navController.navigate(NOTES_SCREEN) }
            )
        }
        composable(NOTES_SCREEN) {
            NotesScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
