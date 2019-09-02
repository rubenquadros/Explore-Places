package com.rubenquadros.yourplaces.di.module

import android.app.Application
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides

@Module
class GoogleMapsModule(private val application: Application) {
    @Provides
    fun provideGoogleApiClient(): GoogleApiClient {
        return GoogleApiClient.Builder(application)
            .addApi(LocationServices.API)
            .build()
    }
}