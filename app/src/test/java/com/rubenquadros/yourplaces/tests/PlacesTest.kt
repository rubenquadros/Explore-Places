package com.rubenquadros.yourplaces.tests

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import com.rubenquadros.yourplaces.base.BaseTest
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

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    override fun setup() {
        super.setup()
        this.activity = Robolectric.setupActivity(FragmentActivity::class.java)
        this.placesViewModel = ViewModelProviders.of(this.activity, viewModelFactory)[PlacesViewModel::class.java]
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

    override fun isMockServerEnabled(): Boolean = true
}