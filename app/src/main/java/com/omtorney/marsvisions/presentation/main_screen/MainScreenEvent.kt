package com.omtorney.marsvisions.presentation.main_screen

sealed class MainScreenEvent {
    data class Load(val rover: String, val sol: String, val camera: String) : MainScreenEvent()
}
