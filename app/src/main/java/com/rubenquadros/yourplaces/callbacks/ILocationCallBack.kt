package com.rubenquadros.yourplaces.callbacks

import android.location.Location
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationSettingsRequest

interface ILocationCallBack {

    fun checkGPS(googleApiClient: GoogleApiClient, locationSetting: LocationSettingsRequest.Builder,
                 locationRequest: LocationRequest, locationListener: LocationListener)

    fun onLocationFetched(location: Location?)
}