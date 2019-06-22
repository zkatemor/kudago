package com.zkatemor.kudago.di

import com.zkatemor.kudago.BuildConfig
import com.zkatemor.kudago.networks.BASE_URL
import com.zkatemor.kudago.networks.CitiesService
import com.zkatemor.kudago.networks.EventsService
import com.zkatemor.kudago.util.CitiesRepository
import com.zkatemor.kudago.util.EventsRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {
    @Singleton
    @Provides
    fun buildHttpClient(): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClientBuilder.addInterceptor(interceptor)
        }
        return okHttpClientBuilder.build()
    }

    @Singleton
    @Provides
    fun buildRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideEventsApi(retrofit: Retrofit): EventsService {
        return retrofit.create(EventsService::class.java)
    }

    @Singleton
    @Provides
    @Named("Events_Repository")
    fun provideEventsRepository(eventsApi: EventsService): EventsRepository {
        return EventsRepository(eventsApi)
    }

    @Singleton
    @Provides
    fun provideCitiesApi(retrofit: Retrofit): CitiesService {
        return retrofit.create(CitiesService::class.java)
    }

    @Singleton
    @Provides
    @Named("Cities_Repository")
    fun provideCitiesRepository(citiesApi: CitiesService): CitiesRepository {
        return CitiesRepository(citiesApi)
    }
}