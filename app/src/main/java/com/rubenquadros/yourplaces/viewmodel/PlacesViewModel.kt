package com.rubenquadros.yourplaces.viewmodel

import android.app.Application
import android.content.Intent
import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.rubenquadros.yourplaces.base.BaseViewModel
import com.rubenquadros.yourplaces.data.local.entity.PlacesEntity
import com.rubenquadros.yourplaces.data.remote.model.nearbyplaces.PlacesResponse
import com.rubenquadros.yourplaces.data.remote.model.searchplaces.SearchPlacesResponse
import com.rubenquadros.yourplaces.data.repository.PlacesRepository
import com.rubenquadros.yourplaces.service.LocationService
import com.rubenquadros.yourplaces.utils.ApplicationConstants
import com.rubenquadros.yourplaces.utils.ApplicationUtility
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class PlacesViewModel @Inject
constructor(private val placesRepository: PlacesRepository,
            @param:Named(ApplicationConstants.SUBSCRIBER_ON) private val subscriberOn: Scheduler,
            @param:Named(ApplicationConstants.OBSERVER_ON) private val observerOn: Scheduler, private val mApplication: Application) : BaseViewModel(mApplication) {

    private lateinit var latLng: String
    val isLoading: MutableLiveData<Boolean?> = MutableLiveData()
    private val placesResponse: MutableLiveData<PlacesResponse> = MutableLiveData()
    private var searchResponse: MutableLiveData<SearchPlacesResponse>? = MutableLiveData()
    private val error: MutableLiveData<String?> = MutableLiveData()
    private val searchError: MutableLiveData<String> = MutableLiveData()
    private var placesEntity = ArrayList<PlacesEntity>()
    private var dbResponse: MutableLiveData<List<PlacesEntity>> = MutableLiveData()
    private lateinit var locationService: LocationService

    fun getNearbyPlaces() {
        placesResponse.value = null
        this.disposable.addAll(this.placesRepository.getNearbyPlaces(latLng)
            .subscribeOn(subscriberOn)
            .observeOn(observerOn)
            .doOnSubscribe { isLoading.value = true }
            .doOnComplete { isLoading.value = false }
            .doOnError { isLoading.value = false }
            .subscribe({ resources -> getNearbyPlacesResponse().postValue(resources) }, {resources -> if(placesResponse.value == null) getErrorResponse().postValue(ApplicationConstants.ERROR) else getErrorResponse().postValue(resources.message)}))
    }

    fun getNearbyPlacesResponse() = placesResponse

    fun getErrorResponse() = error

    fun searchPlaces(place: String) {
        searchResponse!!.value = null
        this.disposable.addAll(this.placesRepository.searchPlaces(place)
            .subscribeOn(subscriberOn)
            .observeOn(observerOn)
            .doOnSubscribe { isLoading.value = true }
            .doOnComplete { isLoading.value = false }
            .doOnError { isLoading.value = false }
            .subscribe({ resources -> getSearchPlacesResponse()!!.postValue(resources) }, { resources-> if(searchResponse!!.value == null) getSearchErrorResponse().postValue(ApplicationConstants.ERROR) else getSearchErrorResponse().postValue(resources.message)}))
    }

    fun getSearchPlacesResponse() = searchResponse

    fun getSearchErrorResponse() = searchError

//    fun getCurrentLocation(){
//        locationService = LocationService()
//        locationService.getCurrentLocation()
//    }
//
//    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        locationService.onActivityResult(requestCode, resultCode, data)
//    }

    fun deletePlaces() {
        this.placesRepository.deletePlaces()
    }

    fun savePlaces(places: PlacesResponse) {
        for(i in places.response?.groups?.get(0)?.items?.indices!!) {
            placesEntity.add(PlacesEntity(i, places.response?.groups?.get(0)?.items?.get(i)?.venue?.location?.lat,
                places.response?.groups?.get(0)?.items?.get(i)?.venue?.location?.lng,
                places.response?.groups?.get(0)?.items?.get(i)?.venue?.name,
                places.response?.groups?.get(0)?.items?.get(i)?.venue?.categories?.get(0)?.name,
                ApplicationUtility.formatList(places.response?.groups?.get(0)?.items?.get(i)?.venue?.location?.formattedAddress!!)))
        }
        this.placesRepository.insertPlaces(placesEntity)
    }

    fun initLocalData() {
        dbResponse = this.placesRepository.getPlacesFromDB()
    }

    fun getResponseFromDB(): LiveData<List<PlacesEntity>> {
        return dbResponse
    }

    fun setLatLng(latLong: String) {
        this.latLng = latLong
    }
}