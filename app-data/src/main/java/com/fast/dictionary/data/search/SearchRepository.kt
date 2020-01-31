package com.fast.dictionary.data.search

import android.util.Log
import com.fast.dictionary.data.search.remote.SearchService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SearchRepository @Inject constructor(private val remoteService: SearchService) {

    // for sample
    fun sayHello() {
        Log.v("TAG", "hello!")
    }
}