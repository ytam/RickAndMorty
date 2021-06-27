package io.github.ytam.rickandmorty

import android.app.Application
import io.github.ytam.rickandmorty.di.appModule
import io.github.ytam.rickandmorty.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(listOf(networkModule, appModule))
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}
