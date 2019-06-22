package com.zkatemor.kudago.app

import android.app.Application
import com.zkatemor.kudago.di.AppComponent
import com.zkatemor.kudago.di.DaggerAppComponent
import com.zkatemor.kudago.di.NetworkModule

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .networkModule(NetworkModule())
            .build()
    }

    companion object {
        var component: AppComponent? = null
            private set
    }
}