package com.omtorney.marsvisions.di

import com.omtorney.marsvisions.data.remote.MarsApi
import com.omtorney.marsvisions.data.remote.MarsApi.Companion.BASE_URL
import com.omtorney.marsvisions.data.repository.RepositoryImpl
import com.omtorney.marsvisions.domain.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMarsApi(): MarsApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(MarsApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRepository(api: MarsApi): Repository {
        return RepositoryImpl(api)
    }
}
