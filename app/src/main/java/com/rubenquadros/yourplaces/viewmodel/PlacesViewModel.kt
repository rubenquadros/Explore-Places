package com.rubenquadros.yourplaces.viewmodel

import androidx.lifecycle.MutableLiveData
import com.rubenquadros.yourplaces.base.BaseViewModel
import com.rubenquadros.yourplaces.data.remote.model.nearbyplaces.PlacesResponse
import com.rubenquadros.yourplaces.data.repository.PlacesRepository
import com.rubenquadros.yourplaces.utils.ApplicationConstants
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class PlacesViewModel @Inject
constructor(private val placesRepository: PlacesRepository,
            @param:Named(ApplicationConstants.SUBSCRIBER_ON) private val subscriberOn: Scheduler,
            @param:Named(ApplicationConstants.OBSERVER_ON) private val observerOn: Scheduler) : BaseViewModel() {

    private lateinit var latLng: String
    val isLoading: MutableLiveData<Boolean?> = MutableLiveData()
    val response: MutableLiveData<PlacesResponse> = MutableLiveData()
    val error: MutableLiveData<String?> = MutableLiveData()

    fun getNearbyPlaces() {
        this.disposable.addAll(this.placesRepository.getNearbyPlaces(latLng)
            .subscribeOn(subscriberOn)
            .observeOn(observerOn)
            .doOnSubscribe { isLoading.value = true }
            .doOnComplete { isLoading.value = false }
            .doOnError { isLoading.value = false }
            .subscribe({ resources -> getNearbyPlacesResponse().postValue(resources) }, {resources -> getErrorResponse().postValue(resources.message)}))
    }

    fun getNearbyPlacesResponse() = response

    fun getErrorResponse() = error

    fun getCurrentLocation() {

    }

    fun setLatLng(latLong: String) {
        this.latLng = latLong
    }
}