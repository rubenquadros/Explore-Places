package com.rubenquadros.yourplaces.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.rubenquadros.yourplaces.base.BaseTest
import com.rubenquadros.yourplaces.data.local.dao.PlacesDAO
import com.rubenquadros.yourplaces.data.local.entity.PlacesEntity
import com.rubenquadros.yourplaces.utils.MockUtils
import com.rubenquadros.yourplaces.viewmodel.PlacesViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.net.HttpURLConnection
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@Suppress("DEPRECATION")
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class PlacesTest: BaseTest() {

    private lateinit var activity: FragmentActivity
    private lateinit var placesViewModel: PlacesViewModel
    private lateinit var placesDAO: PlacesDAO

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setup() {
        super.setup()
        this.activity = Robolectric.setupActivity(FragmentActivity::class.java)
        this.placesViewModel = ViewModelProviders.of(this.activity, viewModelFactory)[PlacesViewModel::class.java]
        this.placesDAO = placesDatabase.placesDAO()
    }

    @Test
    fun loadPlacesSuccess() {
        this.mockResponse("getSuccessResponse.json", HttpURLConnection.HTTP_OK)
        assertEquals(null, this.placesViewModel.getNearbyPlacesResponse().value, "Response should be null because stream is not started yet")
        this.placesViewModel.setLatLng("12.95,77.55")
        this.placesViewModel.getNearbyPlaces()
        assertEquals(MockUtils.nearbyplacesResponse().response?.groups?.size, this.placesViewModel.getNearbyPlacesResponse().value?.response?.groups?.size, "Response must be fetched")
        assertEquals(false, this.placesViewModel.isLoading.value, "Should be reset to 'false' because stream ended")
        assertEquals(null, this.placesViewModel.getErrorResponse().value, "No error must be founded")
    }

    @Test
    fun loadPlacesFail() {
        this.mockResponse("getSuccessResponse.json", HttpURLConnection.HTTP_BAD_GATEWAY)
        assertEquals(null, this.placesViewModel.getNearbyPlacesResponse().value, "Response should be null because stream is not started yet")
        this.placesViewModel.setLatLng("12.95,77.55")
        this.placesViewModel.getNearbyPlaces()
        assertEquals(null, this.placesViewModel.getNearbyPlacesResponse().value, "Response must be null because of HTTP error")
        assertEquals(false, this.placesViewModel.isLoading.value, "Should be reset to 'false' because stream ended")
        assertNotEquals(null, this.placesViewModel.getErrorResponse().value, "Error value must not be empty")
    }

    @Test
    fun insertAndRetrievePlacesSuccess() {
        placesDAO.insertAll(MockUtils.getPlacesEntity())
        val places = getValue(placesDAO.getPlaces())
        assertEquals(MockUtils.getPlacesEntity()[0].name, places.name, "Data should be successfully inserted and retrieved")
    }

    @Test
    fun deletePlacesSuccess() {
        placesDAO.deleteAll()
        assertEquals(0, placesDAO.getPlaces().size, "Data should be successfully deleted")
    }

    @Test
    fun searchPlacesSuccess() {
        this.mockResponse("getSearchSuccessResponse.json",HttpURLConnection.HTTP_OK)
        assertEquals(null, this.placesViewModel.getSearchPlacesResponse()?.value, "Response should be null because stream is not started yet")
        this.placesViewModel.searchPlaces("koramangala")
        assertEquals(MockUtils.searchPlacesResponse().response?.venues?.size, this.placesViewModel.getSearchPlacesResponse()?.value?.response?.venues?.size, "Response must be fetched")
        assertEquals(false, this.placesViewModel.isLoading.value, "Should be reset to 'false' because stream ended")
        assertEquals(null, this.placesViewModel.getSearchErrorResponse().value, "No error must be founded")
    }

    @Test
    fun searchPlacesFail() {
        this.mockResponse("getSearchSuccessResponse.json",HttpURLConnection.HTTP_BAD_GATEWAY)
        assertEquals(null, this.placesViewModel.getSearchPlacesResponse()?.value, "Response should be null because stream is not started yet")
        this.placesViewModel.searchPlaces("koramangala")
        assertEquals(null, this.placesViewModel.getSearchPlacesResponse()?.value, "Response must be null because of HTTP error")
        assertEquals(false, this.placesViewModel.isLoading.value, "Should be reset to 'false' because stream ended")
        assertNotEquals(null, this.placesViewModel.getSearchErrorResponse().value, "Error value must not be empty")
    }

    private fun getValue(places: List<PlacesEntity>): PlacesEntity {
        return places[0]
    }

    override fun isMockServerEnabled(): Boolean = true
}