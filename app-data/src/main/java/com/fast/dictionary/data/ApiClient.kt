package com.fast.dictionary.data

interface ApiClient {
    fun <T : Any> createService(`class`: Class<T>): T
}