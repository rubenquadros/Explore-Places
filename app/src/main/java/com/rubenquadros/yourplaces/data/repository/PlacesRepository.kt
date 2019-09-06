package com.rubenquadros.yourplaces.data.repository

import androidx.lifecycle.MutableLiveData
import com.rubenquadros.yourplaces.data.datacallback.IDBCallBack
import com.rubenquadros.yourplaces.data.local.dao.PlacesDAO
import com.rubenquadros.yourplaces.data.local.entity.PlacesEntity
import com.rubenquadros.yourplaces.data.remote.api.PlacesApiService
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class PlacesRepository(private val placesDAO: PlacesDAO, private val placesApi: PlacesApiService) {

    private var placesData: List<PlacesEntity> = ArrayList()
    private var places: MutableLiveData<List<PlacesEntity>> = MutableLiveData()

    fun getNearbyPlaces(latLng: String) = this.placesApi.getNearbyPlaces(latLng)

    fun searchPlaces(place: String) = this.placesApi.searchNearByPlaces(place)

    fun deletePlaces() {
        doAsync {
            placesDAO.deleteAll()
        }
    }

    fun insertPlaces(places: ArrayList<PlacesEntity>) {
        doAsync {
            placesDAO.insertAll(places)
        }
    }

    fun getPlacesFromDB(): MutableLiveData<List<PlacesEntity>> {
        setPlacesFromDB(object : IDBCallBack {
            override fun onQueryExecuted(placesData: List<PlacesEntity>) {
                places.value = placesData
            }
        })
        return places
    }

    private fun setPlacesFromDB(dbCallback: IDBCallBack) {
        doAsync {
            placesData = placesDAO.getPlaces()
            uiThread { dbCallback.onQueryExecuted(placesData) }
        }

    }
}