package com.fast.dictionary

import android.app.Application
import com.fast.dictionary.data.RemoteModule
import com.fast.dictionary.data.search.SearchRepository
import com.fast.dictionary.data.search.SearchRepositoryModule
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import javax.inject.Inject

class FastDictApplication: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .remoteModule(RemoteModule(BuildConfig.BASE_URL))
            .searchRepositoryModule(SearchRepositoryModule())
            .build()
    }

    // for sample
    @Inject lateinit var searchRepository: SearchRepository

    override fun onCreate() {
        super.onCreate()
        searchRepository.sayHello() // it logs "hello!" with tag name TAG
    }
}