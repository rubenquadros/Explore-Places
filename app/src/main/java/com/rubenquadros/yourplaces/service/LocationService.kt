package com.rubenquadros.yourplaces.service

import com.google.android.gms.common.api.GoogleApiClient
import javax.inject.Inject

class LocationService{

    @Inject
    private lateinit var googleApiClient: GoogleApiClient

    init {
        initGoogleClientApi()
    }

    fun getCurrentLocation() {

    }

    private fun initGoogleClientApi() {
        googleApiClient.connect()
    }
}