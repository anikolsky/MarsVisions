package com.omtorney.marsvisions.data.remote

import com.omtorney.marsvisions.BuildConfig
import com.omtorney.marsvisions.data.remote.dto.PhotosDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MarsApi {

    @GET("/mars-photos/api/v1/rovers/{rover}/photos")
    suspend fun getPhotos(
        @Path("rover") rover: String,
        @Query("sol") sol: Int,
        @Query("camera") camera: String,
//        @Query("page") page: Int = 1,
        @Query("api_key") apiKey: String = API_KEY
    ): PhotosDto

    companion object {
        const val BASE_URL = "https://api.nasa.gov"
        const val API_KEY = BuildConfig.API_KEY
    }
}
