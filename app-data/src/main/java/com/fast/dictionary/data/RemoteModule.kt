package com.fast.dictionary.data

import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RemoteModule(private val baseUrl: String) {


    @Provides
    @Singleton
    fun provideApiClient(): ApiClient {
        return ApiClientImpl(ApiHttpInterceptor(), baseUrl)
    }
}