package com.omtorney.marsvisions.presentation.main_screen.components

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.ImageRequest
import com.omtorney.marsvisions.domain.model.Photo

@Composable
fun ImageCard(
    photo: Photo,
    index: Int,
    context: Context,
    onClick: (String) -> Unit
) {
    Card(
        shape = MaterialTheme.shapes.extraSmall,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(photo.url) }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            SubcomposeAsyncImage(
                model = ImageRequest.Builder(context)
                    .data(photo.url)
                    .crossfade(300)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Fit,
                loading = { CircularProgressIndicator() },
                alignment = Alignment.Center
            )
            Text(
                text = "${index + 1} • ${photo.sol} sol • ${photo.earthDate}",
                modifier = Modifier.padding(4.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
