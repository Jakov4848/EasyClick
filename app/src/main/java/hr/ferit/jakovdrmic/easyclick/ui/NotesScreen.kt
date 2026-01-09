package hr.ferit.jakovdrmic.easyclick.ui
import android.media.SoundPool
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import hr.ferit.jakovdrmic.easyclick.MainActivity
import hr.ferit.jakovdrmic.easyclick.data.model.Note
import hr.ferit.jakovdrmic.easyclick.viewmodel.NotesViewModel

@Composable
fun NotesScreen(
    notesViewModel: NotesViewModel = viewModel(),
    navController: NavController
) {
    val notes = notesViewModel.notes
    val text = notesViewModel.text

    Box(modifier = Modifier.fillMaxSize()) {

        // Arrow back icon (navigate to Metronome)

        IconButton(
            onClick = { navController.navigate("metronome_screen") },
            modifier = Modifier
                .align(Alignment.TopEnd) // top-left
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
            verticalArrangement = Arrangement.Bottom


        ) {

            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = text,
                onValueChange = { notesViewModel.onTextChange(it) },
            )

            Button(
                onClick = { notesViewModel.addNote() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Note")

            }
            Spacer(modifier = Modifier.height(16.dp))

            notes.forEach {
                Text(it.text)
            }
        }
    }


}



