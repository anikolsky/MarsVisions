package com.omtorney.marsvisions.domain.repository

import com.omtorney.marsvisions.domain.model.Photo
import com.omtorney.marsvisions.util.Resource
import kotlinx.coroutines.flow.Flow

interface Repository {
    fun fetchPhotos(rover: String, sol: Int, camera: String): Flow<Resource<List<Photo>>>
}
