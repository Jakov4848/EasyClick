package hr.ferit.jakovdrmic.easyclick.ui
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import hr.ferit.jakovdrmic.easyclick.MainActivity
import hr.ferit.jakovdrmic.easyclick.data.model.Note

@Composable
fun NotesScreen(
    onNavigateBack: () -> Unit
){
    var notes by remember { mutableStateOf(listOf<Note>()) }
    var text by remember { mutableStateOf("") }

    Box(
        modifier = Modifier.fillMaxSize()
    ){
        //  Navigate to metronome screen
        IconButton(
            onClick = onNavigateBack,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                modifier = Modifier.size(28.dp)
            )
        }




        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ){
            Spacer(modifier = Modifier.height(32.dp))

            OutlinedTextField(
                value = text,
                onValueChange = { text = it },
                label = { Text("New note") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        notes = notes + Note(text = text)
                        text = ""
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Add Note")

            }
            Spacer(modifier = Modifier.height(16.dp))

            notes.forEach { note ->
                Text(
                    text = "• ${note.text}",
                    fontSize = 16.sp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

            }


        }

    }


}
