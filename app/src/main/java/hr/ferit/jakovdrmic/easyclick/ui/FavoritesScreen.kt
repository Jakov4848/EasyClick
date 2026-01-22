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
import androidx.compose.material.icons.filled.MusicNote
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hr.ferit.jakovdrmic.easyclick.viewmodel.SoundViewModel

@Composable
fun FavoritesScreen(
    navController: NavController,
    soundViewModel: SoundViewModel = viewModel()
) {
    val favoriteSounds = soundViewModel.favoriteSounds


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0x7393B3F0))

    ) {

        IconButton(
            onClick = { navController.navigate("sounds_screen") },
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(32.dp)
        ) {
            Icon(
                imageVector = Icons.Default.MusicNote,
                contentDescription = "Favorites",
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
                items(items=favoriteSounds, key = { it.id }) { sound ->

                    val isSelected = sound == soundViewModel.selectedSound.value

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected) Color.Cyan else Color.White
                            )
                            .clickable {
                                soundViewModel.selectSound(sound) //update viewmodel state
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

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                imageVector = if (soundViewModel.isFavorite(sound))
                                    Icons.Filled.Star
                                else
                                    Icons.Outlined.Star,
                                contentDescription = "Favorite",
                                tint = if (soundViewModel.isFavorite(sound))
                                    Color.Yellow
                                else
                                    Color.Gray,
                                modifier = Modifier
                                    .padding(start = 10.dp)
                                    .clickable{
                                        soundViewModel.toggleFavorite(sound)
                                    }

                            )
                        }
                    }
                }

            }
        }
    }
}
