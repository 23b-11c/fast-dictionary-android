package com.fast.dictionary

import android.app.Application
import com.facebook.stetho.Stetho
import com.fast.dictionary.network.FastRetrofit

class FastDictionaryApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initRetrofit()
        initStetho()
    }

    private fun initRetrofit() {
        FastRetrofit.init(this)
    }

    private fun initStetho() {
        Stetho.initializeWithDefaults(this)
    }
}