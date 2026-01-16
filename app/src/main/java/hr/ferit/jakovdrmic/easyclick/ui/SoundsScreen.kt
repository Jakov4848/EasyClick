package hr.ferit.jakovdrmic.easyclick.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hr.ferit.jakovdrmic.easyclick.data.model.Sound
import hr.ferit.jakovdrmic.easyclick.data.model.SoundCategory

@Composable
fun SoundsScreen(
    navController: NavController
) {

    // temp list
    val sounds = listOf(
        Sound("1", "click1", "res/raw/click.mp3", SoundCategory.CLASSIC),
        Sound(id = "2", name = "click2", fileUrl = "res/raw/click2.mp3", category = SoundCategory.CLASSIC),
        Sound(id = "3", name = "click3", fileUrl = "res/raw/click3.mp3", category = SoundCategory.CLASSIC),
        Sound(id = "4", name = "click3", fileUrl = "res/raw/click4.mp3", category = SoundCategory.CLASSIC),
        Sound(id = "5", name = "click3", fileUrl = "res/raw/click5.mp3", category = SoundCategory.CLASSIC)
    )

    var selectedSoundID by remember { mutableStateOf<String?>(null) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x7393B3F0))

    ) {

        IconButton(
            onClick = { navController.navigate("metronome_screen") },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Back",
                modifier = Modifier.size(28.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {

            Spacer(modifier = Modifier.height(48.dp))

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(items=sounds, key = { it.id }) { sound ->

                    val isSelected = sound.id == selectedSoundID

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected) Color.Cyan else Color.White
                            )
                            .clickable {
                                selectedSoundID = sound.id
                            }
                            .padding(16.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.MusicNote,
                                contentDescription = "Sound",
                                tint = if (isSelected) Color.White else Color.Black
                            )

                            Spacer(modifier = Modifier.width(16.dp))

                            Text(
                                text = sound.name,
                                fontSize = 18.sp,
                                color = if (isSelected) Color.White else Color.Black,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }
                }

            }
        }
    }
}