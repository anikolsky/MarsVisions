package com.omtorney.marsvisions.presentation.main_screen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.omtorney.marsvisions.presentation.main_screen.components.CustomSpinner

@Composable
fun MainScreen(
    state: MainScreenState,
    onEvent: (MainScreenEvent) -> Unit
) {
    val context = LocalContext.current
    var selectedRover by remember { mutableStateOf(Rover.CURIOSITY.title) }
    var selectedCamera by remember { mutableStateOf(Camera.FHAZ.title) }
    var sol by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = state.error) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        CustomSpinner(
            items = Rover.values().map { it.title },
            selectedItem = selectedRover,
            onItemSelected = { selectedRover = it }
        )
        CustomSpinner(
            items = Camera.values().map { it.title },
            selectedItem = selectedCamera,
            onItemSelected = { selectedCamera = it }
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        ) {
            Text(text = "Sol: ")
            SolButton(onClick = { sol = (sol - 1000).coerceAtLeast(0) }, text = "-1000")
            SolButton(onClick = { sol = (sol - 100).coerceAtLeast(0) }, text = "-100")
            SolButton(onClick = { sol = (sol - 10).coerceAtLeast(0) }, text = "-10")
            SolButton(onClick = { sol = (sol - 1).coerceAtLeast(0) }, text = "-1")
            Text(text = "$sol", fontSize = 20.sp)
            SolButton(onClick = { sol += 1 }, text = "+1")
            SolButton(onClick = { sol += 10 }, text = "+10")
            SolButton(onClick = { sol += 100 }, text = "+100")
            SolButton(onClick = { sol += 1000 }, text = "+1000")
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    onEvent(
                        MainScreenEvent.Load(
                            selectedRover,
                            sol.toString(),
                            selectedCamera
                        )
                    )
                },
                shape = MaterialTheme.shapes.extraSmall,
                colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colorScheme.onBackground)
            ) {
                Text(text = "Load")
            }
            Text(
                text = "Photos taken: ${state.photos.size}",
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
        LazyColumn {
            items(state.photos) { photo ->
                Card(
                    shape = MaterialTheme.shapes.extraSmall,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        SubcomposeAsyncImage(
                            model = ImageRequest.Builder(context)
                                .data(photo.url)
                                .crossfade(true)
                                .build(),
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            loading = { CircularProgressIndicator() },
                            alignment = Alignment.Center
                        )
                        Text(
                            text = "Sol: ${photo.sol} / Earth date: ${photo.earthDate}",
                            modifier = Modifier.padding(4.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun SolButton(
    text: String,
    onClick: () -> Unit
) {
    Text(
        text = text,
        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
        modifier = Modifier.clickable { onClick() }
    )
}
