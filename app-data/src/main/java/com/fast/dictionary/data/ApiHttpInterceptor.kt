package com.fast.dictionary.data

import okhttp3.Interceptor
import okhttp3.Response

class ApiHttpInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return chain.proceed(chain.request())
    }
}