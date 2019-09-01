package com.rubenquadros.yourplaces.data.repository

import com.rubenquadros.yourplaces.data.local.dao.PlacesDAO
import com.rubenquadros.yourplaces.data.remote.api.PlacesApiService

class PlacesRepository(private val placesDAO: PlacesDAO, private val placesApi: PlacesApiService) {

    fun getNearbyPlaces(latLng: String) = this.placesApi.getNearbyPlaces(latLng)
}