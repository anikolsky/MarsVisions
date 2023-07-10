package com.omtorney.marsvisions.presentation.main_screen

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.omtorney.marsvisions.presentation.main_screen.components.CustomSpinner
import com.omtorney.marsvisions.presentation.main_screen.components.ImageCard
import com.omtorney.marsvisions.presentation.main_screen.components.SolButton

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainScreen(
    state: MainScreenState,
    onEvent: (MainScreenEvent) -> Unit,
    onClick: (String) -> Unit
) {
    val context = LocalContext.current
    var selectedRover by rememberSaveable { mutableStateOf(Rover.CURIOSITY.title) }
    var selectedCamera by rememberSaveable { mutableStateOf("") }
    var sol by remember { mutableStateOf(0) }

    LaunchedEffect(key1 = state.error) {
        if (state.error != null) {
            Toast.makeText(context, state.error, Toast.LENGTH_SHORT).show()
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onEvent(MainScreenEvent.Load(selectedRover, sol.toString()))
            }) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null
                )
            }
        },
        modifier = Modifier.padding(8.dp)
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            CustomSpinner(
                items = Rover.values().map { it.title },
                selectedItem = selectedRover,
                onItemSelected = { selectedRover = it }
            )
            CustomSpinner(
                items = state.photos.map { it.camera.name }.distinct(),
                selectedItem = selectedCamera,
                onItemSelected = { selectedCamera = it }
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Sol: ")
                SolButton(onClick = { sol = (sol - 1000).coerceAtLeast(0) }, text = "-1000")
                SolButton(onClick = { sol = (sol - 100).coerceAtLeast(0) }, text = "-100")
                SolButton(onClick = { sol = (sol - 10).coerceAtLeast(0) }, text = "-10")
                SolButton(onClick = { sol = (sol - 1).coerceAtLeast(0) }, text = "-1")
                Text(text = "$sol", fontSize = 18.sp)
                SolButton(onClick = { sol += 1 }, text = "+1")
                SolButton(onClick = { sol += 10 }, text = "+10")
                SolButton(onClick = { sol += 100 }, text = "+100")
                SolButton(onClick = { sol += 1000 }, text = "+1000")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Photos loaded: ${state.photos.size}")
                if (state.isLoading) LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }
            LazyVerticalStaggeredGrid(
                columns = StaggeredGridCells.Fixed(2),
                verticalItemSpacing = 8.dp,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                itemsIndexed(state.photos.filter { it.camera.name == selectedCamera.uppercase() }) { index, photo ->
                    ImageCard(
                        photo = photo,
                        index = index,
                        context = context,
                        onClick = onClick
                    )
                }
            }
        }
    }
}
