package com.android.locationmvvm.ui.main

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.locationmvvm.R
import com.android.locationmvvm.utils.Constants.RequestCode.GPS_REQUEST_CODE
import com.github.florent37.runtimepermission.kotlin.askPermission
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.snc.it.inventory.data.datasources.room.LocationModel
import com.android.locationmvvm.utils.GpsUtils
import com.google.android.gms.maps.CameraUpdateFactory
import kotlinx.android.synthetic.main.main_fragment.*


class LocationFragment : Fragment() {
    private var isGPSEnabled = false
    private lateinit var viewModel: LocationViewModel

    private var mapReady = false
    private lateinit var mMap: GoogleMap

    companion object {
        const val MAP_ZOOM = 15F

        fun newInstance() = LocationFragment()
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        var rootView = inflater.inflate(R.layout.main_fragment, container, false)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync { googleMap ->
            mMap = googleMap
            mapReady = true
            showPermissionDialog()
        }

        return rootView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)
        viewModel.apply {

            mlocation.observe(this@LocationFragment, Observer {
                latLong?.text = "Latitude ${it.latitude} and Longitude ${it.longitude}"
                updateMap(it)
            })
        }
    }

    override fun onResume() {
        super.onResume()
        startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        viewModel?.stopLocationUpdates()
    }

    override fun onStop() {
        super.onStop()
        viewModel?.stopLocationUpdates()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("onActivityResult()", Integer.toString(resultCode))

        //final LocationSettingsStates states = LocationSettingsStates.fromIntent(data);
        when (requestCode) {
            GPS_REQUEST_CODE -> when (resultCode) {
                Activity.RESULT_OK -> {

                    // All required changes were successfully made
                    Toast.makeText(activity, "Location enabled by user!", Toast.LENGTH_LONG)
                        .show()
                    this@LocationFragment.isGPSEnabled = true
                    startLocationUpdates()
                }
                Activity.RESULT_CANCELED -> {

                    // The user was asked to change settings, but chose not to
                    Toast.makeText(
                        activity,
                        "Location not enabled, user cancelled.",
                        Toast.LENGTH_LONG
                    ).show()
                }
                else -> {
                }
            }
        }
    }

    /**
     * This function is used to ask User Location permission
     */
    fun showPermissionDialog() {
        askPermission(Manifest.permission.ACCESS_FINE_LOCATION) {
            showGPSEnableDialog()
        }.onDeclined { e ->
            if (e.hasDenied()) {
                Log.d("Permission", "Permission Denied")
            }

            if (e.hasForeverDenied()) {
                Log.d("Permission", "Permission Denied Forever")
            }
        }
    }

    /**
     * This Function is used to ask user for LocationRequest, inshort turning on the GPS
     */
    fun showGPSEnableDialog() {
        GpsUtils(requireContext()).turnGPSOn(object : GpsUtils.OnGpsListener {
            override fun gpsStatus(isGPSEnable: Boolean) {
                this@LocationFragment.isGPSEnabled = isGPSEnable
                startLocationUpdates()
            }
        })
    }

    private fun startLocationUpdates() {
        if (mapReady && isGPSEnabled) {
            viewModel.startLocationUpdates()
        }
    }

    /**
     * This Function is used to update user current location on the Map
     */
    private fun updateMap(location: LocationModel) {
        if (mapReady) {
            location.apply {
                val marker = LatLng(latitude.toDouble(), longitude.toDouble())
                mMap.clear()
                mMap.addMarker(MarkerOptions().position(marker).title("You are here"))
                mMap.animateCamera(
                    CameraUpdateFactory.newLatLngZoom(
                        marker,
                        MAP_ZOOM
                    )
                );
            }
        }
    }

}
