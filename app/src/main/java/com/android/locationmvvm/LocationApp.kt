package com.android.locationmvvm

import android.app.Application

class LocationApp : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this

    }

    companion object {
        lateinit var instance: LocationApp
    }
}