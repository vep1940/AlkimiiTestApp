package com.vep1940.alkimiitestapp

import android.app.Application
import com.vep1940.alkimiitestapp.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@BaseApplication)
            modules(appModule)
        }
    }
}