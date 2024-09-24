package com.jge.androidfetch

import android.app.Application
import com.jge.androidfetch.injection.ApplicationComponent
import com.jge.androidfetch.injection.DaggerApplicationComponent
import com.jge.androidfetch.injection.NetworkModule
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AndroidFetchApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        this.applicationComponent = initDagger()
    }

    private fun initDagger() = DaggerApplicationComponent
        .builder()
        .networkModule(NetworkModule(this))
        .build()
}