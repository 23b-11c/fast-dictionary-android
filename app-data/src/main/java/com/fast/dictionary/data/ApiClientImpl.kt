package com.fast.dictionary.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiClientImpl @Inject constructor(interceptor: ApiHttpInterceptor, baseUrl: String): ApiClient {
    companion object {
        private const val TIMEOUT_CONNECT = 10L
    }

    private val retrofit: Retrofit

    init {
        val okHttpClient = createHttpClient(interceptor, createLoggingInterceptor())
        retrofit = createRetrofitInstance(okHttpClient, baseUrl)
    }

    private fun createLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = when {
                BuildConfig.DEBUG -> HttpLoggingInterceptor.Level.BODY
                else -> HttpLoggingInterceptor.Level.NONE
            }
        }
    }

    private fun createHttpClient(vararg interceptors: Interceptor): OkHttpClient {
        return OkHttpClient.Builder().let { builder ->
            builder.connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            builder.writeTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)

            interceptors.forEach { interceptor ->
                builder.addInterceptor(interceptor)
            }

            builder.build()
        }
    }

    private fun createRetrofitInstance(httpClient: OkHttpClient, baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(httpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    override fun <T : Any> createService(`class`: Class<T>): T = retrofit.create(`class`)
}