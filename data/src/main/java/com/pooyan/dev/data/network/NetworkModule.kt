package com.pooyan.dev.data.network

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        gsonBuilder.setLenient()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun provideOkHttpClientBuilder(@ApplicationContext context: Context): OkHttpClient.Builder {
        val httpCacheDirectory = File(context.cacheDir, "httpCache")
        val cache = Cache(httpCacheDirectory, (10 * 1024 * 1024).toLong())

        val okHttpClientBuilder = OkHttpClient().newBuilder()
        okHttpClientBuilder.readTimeout(10, TimeUnit.SECONDS)
        okHttpClientBuilder.connectTimeout(10, TimeUnit.SECONDS)
        okHttpClientBuilder.cache(cache)

        return okHttpClientBuilder
    }

    @Provides
    @Singleton
    internal fun provideInterceptor(): Interceptor =
        HttpLoggingInterceptor { Log.d("NetworkModule", it) }
            .apply { level = HttpLoggingInterceptor.Level.BODY }

    @Provides
    @Singleton
    internal fun provideOkHttpClient(
        builder: OkHttpClient.Builder,
        interceptor: Interceptor,
    ): OkHttpClient {
        builder.addInterceptor(interceptor)
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        @ApplicationContext context: Context,
    ): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://higherorlower-api.netlify.app/")
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideVideoApiService(retrofit: Retrofit): GuessCardGameApiService =
        retrofit.create(GuessCardGameApiService::class.java)

}