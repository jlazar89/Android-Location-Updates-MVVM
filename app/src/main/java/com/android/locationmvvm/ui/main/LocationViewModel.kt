package com.android.locationmvvm.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.locationmvvm.LocationApp
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.snc.it.inventory.data.datasources.room.LocationModel

class LocationViewModel : ViewModel() {
    var mlocation = MutableLiveData<LocationModel>()
    private var fusedLocationClient =
        LocationServices.getFusedLocationProviderClient(LocationApp.instance)

    companion object {
        val locationRequest: LocationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations) {
                setLocationData(location)
            }
        }
    }

    /**
     * Livedata of Current location
     */
    private fun setLocationData(location: Location) {
        mlocation.value = LocationModel(
            longitude = location.longitude,
            latitude = location.latitude
        )
    }

    /**
     * Function to start location updates
     */
    @SuppressLint("MissingPermission")
    fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            null
        )
    }

    /**
     * Function to stop location updates
     */
    fun stopLocationUpdates(){
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }
}
