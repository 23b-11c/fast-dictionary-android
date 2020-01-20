package com.fast.dictionary.network

import android.content.Context
import android.os.StatFs
import android.util.Log
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.fast.dictionary.BuildConfig
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToLong
import kotlin.reflect.KClass

object FastRetrofit {

    private const val TAG = "FastRetrofit"

    private const val DEFAULT_URL = "http://" // TODO Set Base URL

    private const val CACHE_DIRECTORY_NAME = "okhttp_client_cache"
    private const val MIN_DISK_CACHE_SIZE: Long = 5L * 1024L * 1024L // 5MB
    private const val MAX_DISK_CACHE_SIZE: Long = 200L * 1024L * 1024L // 200MB

    private val httpLoggingInterceptor = HttpLoggingInterceptor { Log.d(TAG, it) }
        .apply { level = HttpLoggingInterceptor.Level.BASIC }

    private lateinit var okHttpClient: OkHttpClient

    fun init(context: Context) {
        init(context, CACHE_DIRECTORY_NAME, ::calculateDiskCacheSize)
    }

    fun init(context: Context, cacheDirectoryName: String, calculateMaxSize: (File) -> Long) {
        okHttpClient = buildOkHttpClient(createCache(context, cacheDirectoryName, calculateMaxSize))
    }

    private fun buildOkHttpClient(cache: Cache): OkHttpClient = OkHttpClient.Builder()
        .apply {
            if (BuildConfig.DEBUG) {
                addInterceptor(httpLoggingInterceptor)
                addNetworkInterceptor(StethoInterceptor())
            }
        }
        .cache(cache)
        .build()

    fun <T : Any> create(
        service: KClass<T>,
        httpUrl: String = DEFAULT_URL,
        client: OkHttpClient = okHttpClient
    ): T = create(service.java, httpUrl, client)

    fun <T> create(
        service: Class<T>,
        httpUrl: String = DEFAULT_URL,
        client: OkHttpClient = okHttpClient
    ): T = Retrofit.Builder()
        .baseUrl(httpUrl)
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(service)

    private fun createCache(
        context: Context,
        cacheDirectoryName: String,
        calculateMaxSize: (File) -> Long
    ): Cache {
        val cacheDirectory = createCacheDirectory(context, cacheDirectoryName)
        val cache = Cache(cacheDirectory, calculateMaxSize(cacheDirectory))
        val sizeText = String.format("%.1fM", cache.size().toFloat() / 1000000f)

        // TODO 로그 확인
        Log.d(TAG, "Create cache for network, ${cacheDirectory.absolutePath}, ${sizeText}B")
        return cache
    }

    private fun createCacheDirectory(context: Context, cacheDirectoryName: String): File {
        val cacheDir = File(context.cacheDir, cacheDirectoryName)
        if (!cacheDir.exists()) {
            cacheDir.mkdirs()
        }
        return cacheDir
    }

    private fun calculateDiskCacheSize(directory: File): Long {
        val statFs = StatFs(directory.absolutePath)
        val available: Long = statFs.blockCountLong * statFs.blockSizeLong
        val size: Long = (available * 0.02).roundToLong()
        return max(MIN_DISK_CACHE_SIZE, min(MAX_DISK_CACHE_SIZE, size))
    }
}
