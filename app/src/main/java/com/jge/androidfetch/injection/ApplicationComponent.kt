package com.jge.androidfetch.injection

import com.jge.androidfetch.presentation.MainActivity
import dagger.Component

@Component(modules = [NetworkModule::class])
interface ApplicationComponent {
    fun inject(mainActivity: MainActivity)
}