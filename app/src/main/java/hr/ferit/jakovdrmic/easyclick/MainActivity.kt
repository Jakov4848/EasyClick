package hr.ferit.jakovdrmic.easyclick

import android.media.SoundPool
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.google.firebase.FirebaseApp

import hr.ferit.jakovdrmic.easyclick.ui.navigation.AppNavigation
import hr.ferit.jakovdrmic.easyclick.ui.theme.EasyClickTheme

class MainActivity : ComponentActivity() {

    private lateinit var soundPool: SoundPool
    private var clickSoundId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)


        // Initialize SoundPool
        soundPool = SoundPool.Builder().setMaxStreams(1).build()
        clickSoundId = soundPool.load(this, R.raw.click, 1)

        setContent {
            EasyClickTheme {
                Surface(
                    modifier = androidx.compose.ui.Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    AppNavigation(
                        soundPool = soundPool
                    )
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        soundPool.release()
    }
}
