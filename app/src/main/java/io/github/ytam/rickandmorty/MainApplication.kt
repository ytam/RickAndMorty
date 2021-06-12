package io.github.ytam.rickandmorty

import android.app.Application
import io.github.ytam.rickandmorty.di.appModule
import io.github.ytam.rickandmorty.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(networkModule, appModule))
        }
    }
}
