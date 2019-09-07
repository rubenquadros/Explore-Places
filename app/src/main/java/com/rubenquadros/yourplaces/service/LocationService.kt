package com.rubenquadros.yourplaces.service

import android.app.Activity
import android.app.Application
import android.content.Intent
import android.location.Location
import android.os.Bundle
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.rubenquadros.yourplaces.callbacks.ILocationCallBack
import com.rubenquadros.yourplaces.utils.ApplicationConstants

@Suppress("NAME_SHADOWING")
class LocationService(private val context: Application): GoogleApiClient.OnConnectionFailedListener, GoogleApiClient.ConnectionCallbacks, LocationListener{

    private lateinit var googleApiClient: GoogleApiClient
    private lateinit var mListener: ILocationCallBack

    private fun getLocation() {
        val mLocationRequest = LocationRequest.create()
        mLocationRequest.interval = 20000 * 20000
        mLocationRequest.fastestInterval = 20000 * 20000
        mLocationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        val builder: LocationSettingsRequest.Builder = LocationSettingsRequest.Builder().addLocationRequest(mLocationRequest)
        builder.setAlwaysShow(true)
        mListener.checkGPS(googleApiClient, builder, mLocationRequest, this)
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

    fun setLocationCallback(listener: ILocationCallBack) {
        this.mListener = listener
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
        mListener.onLocationFetched(p0)
    }
}