package com.fast.dictionary

import android.app.Application
import com.fast.dictionary.data.RemoteModule
import com.fast.dictionary.data.search.SearchRepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton


@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        RemoteModule::class,
        SearchRepositoryModule::class,
        ApplicationModule::class
    ]
)
interface AppComponent: AndroidInjector<FastDictApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun remoteModule(remoteModule: RemoteModule): AppComponent.Builder

        fun searchRepositoryModule(searchRepositoryModule: SearchRepositoryModule): AppComponent.Builder

        fun build(): AppComponent
    }
}