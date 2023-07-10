package com.omtorney.marsvisions.data.repository

import com.omtorney.marsvisions.data.remote.MarsApi
import com.omtorney.marsvisions.data.remote.dto.toPhotos
import com.omtorney.marsvisions.domain.model.Photo
import com.omtorney.marsvisions.domain.repository.Repository
import com.omtorney.marsvisions.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException

class RepositoryImpl(
    private val api: MarsApi
) : Repository {

    override fun fetchPhotos(rover: String, sol: Int): Flow<Resource<List<Photo>>> = flow {
        try {
            emit(Resource.Loading())
            val response = api.getPhotos(rover, sol)
            val photos = response.toPhotos()
            emit(Resource.Success(photos))
        } catch (e: IOException) {
            emit(Resource.Error(message = "Connection error: ${e.localizedMessage}"))
        } catch (e: Exception) {
            emit(Resource.Error(message = "Unexpected error: ${e.localizedMessage}"))
        }
    }
}
