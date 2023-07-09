package com.omtorney.marsvisions.domain.model

class Photo(
    val url: String,
    val sol: Int,
    val earthDate: String,
    val rover: Rover,
    val camera: Camera
) {
    data class Rover(
        val id: Int,
        val name: String,
        val landingDate: String,
        val launchDate: String,
        val status: String,
        val maxSol: Int,
        val maxDate: String,
        val totalPhotos: Int
    )
    data class Camera(
        val name: String,
        val fullName: String
    )
}
