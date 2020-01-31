package com.fast.dictionary.data.search

import com.fast.dictionary.data.ApiClient
import com.fast.dictionary.data.search.remote.SearchService
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SearchRepositoryModule {

    @Provides
    @Singleton
    fun provideSearchService(apiClient: ApiClient): SearchService {
        return apiClient.createService(SearchService::class.java)
    }

    @Provides
    @Singleton
    fun provideSearchRepository(searchService: SearchService): SearchRepository {
        return SearchRepository(searchService)
    }

}