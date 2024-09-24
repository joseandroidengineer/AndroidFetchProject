package com.jge.androidfetch.injection

import androidx.lifecycle.ViewModelProvider
import com.jge.androidfetch.AndroidFetchApplication
import com.jge.androidfetch.data.FetchApi
import com.jge.androidfetch.data.FetchRepo
import com.jge.androidfetch.data.FetchRepoImpl
import com.jge.androidfetch.presentation.FetchViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule() {
    constructor(application: AndroidFetchApplication) : this()

    @Provides
    fun provideFetchApi(): FetchApi {
        return Retrofit.Builder()
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FetchApi::class.java)
    }

    @Provides
    fun provideFetchRepo(fetchApi: FetchApi): FetchRepo {
        return FetchRepoImpl(fetchApi)
    }

    @Provides
    fun provideFetchViewModelFactory(fetchRepoImpl: FetchRepoImpl): ViewModelProvider.Factory{
        return FetchViewModelFactory(fetchRepoImpl)
    }
}