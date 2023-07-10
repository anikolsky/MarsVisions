package com.omtorney.marsvisions.presentation.main_screen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.omtorney.marsvisions.domain.repository.Repository
import com.omtorney.marsvisions.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    var state by mutableStateOf(MainScreenState())
        private set

    fun onEvent(event: MainScreenEvent) {
        when (event) {
            is MainScreenEvent.Load -> fetchPhotos(event.rover, event.sol)
        }
    }

    private fun fetchPhotos(rover: String, sol: String) {
        viewModelScope.launch {
            repository.fetchPhotos(rover, sol.toInt()).collect { result ->
                state = when (result) {
                    is Resource.Loading -> {
                        state.copy(isLoading = true)
                    }
                    is Resource.Success -> {
                        state.copy(photos = result.data ?: emptyList(), isLoading = false)
                    }
                    is Resource.Error -> {
                        state.copy(error = result.message, isLoading = false)
                    }
                }
            }
        }
    }
}

enum class Rover(val title: String) {
    CURIOSITY("curiosity"),
    OPPORTUNITY("opportunity"),
    SPIRIT("spirit")
}

enum class Camera(val title: String) {
    FHAZ("fhaz"),
    RHAZ("rhaz"),
    MAST("mast"),
    CHEMCAM("chemcam"),
    MAHLI("mahli"),
    MARDI("mardi"),
    NAVCAM("navcam"),
    PANCAM("pancam"),
    MINITES("minites")
}
