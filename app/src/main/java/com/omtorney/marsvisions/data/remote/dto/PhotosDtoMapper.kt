package com.omtorney.marsvisions.data.remote.dto

import com.omtorney.marsvisions.domain.model.Photo

fun PhotosDto.toPhotos(): List<Photo> = photos.map { photo ->
    with(photo) {
        Photo(
            url = img_src,
            sol = sol,
            earthDate = earth_date,
            rover = Photo.Rover(
                id = rover.id,
                name = rover.name,
                landingDate = rover.landing_date,
                launchDate = rover.launch_date,
                status = rover.status,
                maxSol = rover.max_sol,
                maxDate = rover.max_date,
                totalPhotos = rover.total_photos
            ),
            camera = Photo.Camera(
                name = camera.name,
                fullName = camera.full_name
            )
        )
    }
}
