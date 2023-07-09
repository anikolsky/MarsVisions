package com.omtorney.marsvisions.presentation.main_screen

import com.omtorney.marsvisions.domain.model.Photo

data class MainScreenState(
    val photos: List<Photo> = emptyList(),
    val error: String? = null
)
