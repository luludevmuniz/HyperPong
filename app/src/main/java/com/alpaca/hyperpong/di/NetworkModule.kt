package com.alpaca.hyperpong.di

import com.alpaca.hyperpong.data.remote.CloudFunctionsApi
import com.alpaca.hyperpong.data.repository.CloudFunctionsRepositoryImpl
import com.alpaca.hyperpong.domain.repository.CloudFunctionsRepository
import com.alpaca.hyperpong.util.Constantes.CLOUD_FUNCTIONS_URL
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(CLOUD_FUNCTIONS_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()
    }

    @Provides
    @Singleton
    fun provideCloudFunctionsApi(retrofit: Retrofit): CloudFunctionsApi =
        retrofit.create(CloudFunctionsApi::class.java)

    @Provides
    @Singleton
    fun provideCloudFunctionsRepository(cloudFunctionsApi: CloudFunctionsApi): CloudFunctionsRepository =
        CloudFunctionsRepositoryImpl(cloudFunctionsApi = cloudFunctionsApi)
}