package com.rubenquadros.yourplaces.service

import android.Manifest
import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.location.*
import com.rubenquadros.yourplaces.utils.ApplicationConstants

@Suppress("NAME_SHADOWING")
class LocationService(private val context: Application): GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener{

    private lateinit var googleApiClient: GoogleApiClient

    private fun getLocation() {
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 1
        mLocationRequest.fastestInterval = 1
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest)
        builder.setAlwaysShow(true)

        val result: PendingResult<LocationSettingsResult> = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        result.setResultCallback { result ->
            val status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> {
                    // All location settings are satisfied.
                    // You can initialize location requests here.
                    val permissionLocation = ContextCompat
                        .checkSelfPermission(context,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, mLocationRequest, this)
                    }
                }
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                    // Location settings are not satisfied.
                    // But could be fixed by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        // Ask to turn on GPS automatically
                        status.startResolutionForResult(context as Activity,
                            ApplicationConstants.REQUEST_CHECK_SETTINGS_GPS)
                    } catch (e: Exception) {
                        // Ignore the error.
                    }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
            }// Location settings are not satisfied. However, we have no way to fix the
            // settings so we won't show the dialog.
            //finish();
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when(requestCode){
            ApplicationConstants.REQUEST_CHECK_SETTINGS_GPS -> {
                when(resultCode){
                    Activity.RESULT_OK -> getLocation()
                    Activity.RESULT_CANCELED -> getLocation()
                }
            }
        }
    }

     fun getCurrentLocation() {
        googleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API)
            .addOnConnectionFailedListener(this)
            .addConnectionCallbacks(this)
            .build()
        googleApiClient.connect()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        // Failed to connect to Maps Api
    }

    override fun onConnected(p0: Bundle?) {
        getLocation()
    }

    override fun onConnectionSuspended(p0: Int) {
        getCurrentLocation()
    }

    override fun onLocationChanged(p0: Location?) {

    }
}