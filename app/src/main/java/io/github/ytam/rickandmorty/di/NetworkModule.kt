package io.github.ytam.rickandmorty.di

import io.github.ytam.rickandmorty.service.RickAndMortyAPI
import io.github.ytam.rickandmorty.utils.Constants
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single<Interceptor> {
        HttpLoggingInterceptor()
            .apply { level = HttpLoggingInterceptor.Level.BODY }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<Interceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get<OkHttpClient>())
            .build()
    }
    single { get<Retrofit>().create(RickAndMortyAPI::class.java) }
}
