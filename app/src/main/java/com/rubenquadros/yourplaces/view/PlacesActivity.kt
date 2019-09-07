package com.rubenquadros.yourplaces.view

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.rubenquadros.yourplaces.R
import com.rubenquadros.yourplaces.adapter.BtmShtRecViewAdapter
import com.rubenquadros.yourplaces.base.BaseActivity
import com.rubenquadros.yourplaces.callbacks.ILocationCallBack
import com.rubenquadros.yourplaces.data.local.entity.PlacesEntity
import com.rubenquadros.yourplaces.data.remote.model.nearbyplaces.PlacesResponse
import com.rubenquadros.yourplaces.data.remote.model.searchplaces.SearchPlacesResponse
import com.rubenquadros.yourplaces.service.LocationService
import com.rubenquadros.yourplaces.utils.ApplicationConstants
import com.rubenquadros.yourplaces.utils.ApplicationUtility
import com.rubenquadros.yourplaces.viewmodel.PlacesViewModel

@Suppress("NAME_SHADOWING")
class PlacesActivity : BaseActivity(), OnMapReadyCallback, ILocationCallBack {

    private lateinit var placesViewModel: PlacesViewModel
    private lateinit var manager: LocationManager
    private lateinit var mMap: GoogleMap
    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var adapter: BtmShtRecViewAdapter
    private lateinit var locationService: LocationService
    private var latitude: Double? = 0.0
    private var longitude: Double? = 0.0

    @BindView(R.id.parent) lateinit var rootLayout: CoordinatorLayout
    @BindView(R.id.progressBar) lateinit var mProgressBar: ProgressBar
    @BindView(R.id.searchView) lateinit var mSearchView: SearchView
    @BindView(R.id.bottomSheet) lateinit var layoutBottomSheet: LinearLayout
    @BindView(R.id.bottomSheetRecView) lateinit var mRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_places)
        ButterKnife.bind(this)
        this.configureDesign()
    }

    private fun configureDesign() {
        this.setupViewModel()
        this.setupMap()
        this.observeData()
        this.setupSearch()
        this.setupBottomSheet()
        //this.getPlaces()
    }

    private fun setupViewModel() {
        placesViewModel = ViewModelProviders.of(this, viewModelFactory)[PlacesViewModel::class.java]
    }

    private fun setupMap() {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapsFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    private fun observeData() {
        this.placesViewModel.getNearbyPlacesResponse().observe(this, Observer{ it?.let { updateUIWithNearbyPlaces(it) } })
        this.placesViewModel.getSearchPlacesResponse()?.observe(this, Observer { it?.let { updateUIWithSearchedPlaces(it) } })
        this.placesViewModel.isLoading.observe(this, Observer { it?.let { shouldShowProgress(it) } })
        this.placesViewModel.getErrorResponse().observe(this, Observer { it?.let { handleError(it) } })
        this.placesViewModel.getSearchErrorResponse().observe(this, Observer { it?.let { handleSearchError(it) } })
    }

    private fun setupSearch() {
        mSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if(!query.isNullOrEmpty()) {
                    placesViewModel.searchPlaces(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun setupBottomSheet() {
        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet)
        val layoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = layoutManager
    }

    private fun getPlaces(latitude: Double?, longitude: Double?) {
        this.latitude = latitude
        this.longitude = longitude
        this.placesViewModel.setLatLng(String.format("%f", latitude)+ "," + String.format("%f", longitude))
        this.placesViewModel.getNearbyPlaces()
    }

    private fun updateUIWithNearbyPlaces(placesResponse: PlacesResponse){
        shouldShowProgress(false)
        layoutBottomSheet.visibility = View.VISIBLE
        if(placesResponse.response != null) {
            this.placesViewModel.deletePlaces()
            this.placesViewModel.savePlaces(placesResponse)
            mMap.clear()
            mMap.addMarker(MarkerOptions()
                .position(LatLng(this.latitude!!,this.longitude!!))
                .anchor(0.5f, 0.5f)
                .draggable(false)
                .icon(ApplicationUtility.bitmapDescriptorFromVector(this, R.drawable.ic_pin_drop_black_24dp))
            )
            for (i in placesResponse.response!!.groups?.get(0)?.items?.indices!!) {
                addMarker(
                    placesResponse.response!!.groups?.get(0)?.items?.get(i)?.venue?.location?.lat,
                    placesResponse.response!!.groups?.get(0)?.items?.get(i)?.venue?.location?.lng)
            }
            adapter = BtmShtRecViewAdapter(placesResponse, null, null)
            mRecyclerView.adapter = adapter
            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(this.latitude!!, this.longitude!!)))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16F))
        }
    }

    private fun updateUIWithSearchedPlaces(searchPlacesResponse: SearchPlacesResponse) {
        shouldShowProgress(false)
        layoutBottomSheet.visibility = View.VISIBLE
        when (searchPlacesResponse.meta?.code) {
            ApplicationConstants.STATUS_OK -> {
                if (searchPlacesResponse.response != null) {
                    mMap.clear()
                    for (i in searchPlacesResponse.response!!.venues?.indices!!) {
                        addMarker(
                            searchPlacesResponse.response!!.venues?.get(i)?.location?.lat,
                            searchPlacesResponse.response!!.venues?.get(i)?.location?.lng
                        )
                        adapter = BtmShtRecViewAdapter(null, searchPlacesResponse, null)
                        mRecyclerView.adapter = adapter
                        mMap.moveCamera(
                            CameraUpdateFactory.newLatLng(
                                LatLng(
                                    searchPlacesResponse.response!!.venues?.get(i)?.location?.lat!!,
                                    searchPlacesResponse.response!!.venues?.get(i)?.location?.lng!!
                                )
                            )
                        )
                        mMap.animateCamera(CameraUpdateFactory.zoomTo(16F))
                    }
                }
            }else -> {
                ApplicationUtility.showSnack(getString(R.string.err_search_place), rootLayout, getString(R.string.ok))
            }
        }
    }

    private fun addMarker(latitude: Double?, longitude: Double?) {
        if(latitude!=null && longitude!=null) {
            mMap.addMarker(
                MarkerOptions()
                    .position(LatLng(latitude, longitude))
                    .draggable(false)
                    .anchor(0.5f, 0.5f)
                    .icon(ApplicationUtility.bitmapDescriptorFromVector(this, R.drawable.ic_place_black_24dp))
            )
        }
    }

    private fun handleError(error: String) {
        shouldShowProgress(false)
        if(error == ApplicationConstants.ERROR) {
            fetchPlacesFromDB()
        }
    }

    private fun handleSearchError(error: String) {
        shouldShowProgress(false)
        if(error == ApplicationConstants.ERROR) {
            ApplicationUtility.showDialog(
                this, getString(R.string.err_search_place_title),
                getString(R.string.err_search_place), getString(R.string.ok)
            )
        }
    }

    private fun fetchPlacesFromDB() {
        this.placesViewModel.initLocalData()
        this.placesViewModel.getResponseFromDB().observe(this, Observer<List<PlacesEntity>> { t ->
            if(t!=null && t.isNotEmpty()) {
                //ApplicationUtility.showToast(this, getString(R.string.no_net_toast))
                updateUIFromLocalData(t)
            }else {
                // No data available
                shouldShowProgress(false)
                ApplicationUtility.showDialog(this, getString(R.string.no_net_title), getString(R.string.no_net), getString(R.string.ok))
            }
        })
    }

    private fun updateUIFromLocalData(places: List<PlacesEntity>) {
        layoutBottomSheet.visibility = View.VISIBLE
        mMap.clear()
        for (i in places.indices) {
            addMarker(places[i].latitude, places[i].longitude)
            adapter = BtmShtRecViewAdapter(null, null, places)
            mRecyclerView.adapter = adapter
            mMap.moveCamera(CameraUpdateFactory.newLatLng(LatLng(places[0].latitude!!, places[0].longitude!!)))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(16F))
        }
    }

    private fun shouldShowProgress(isFetching: Boolean) {
        if(isFetching) {
            mProgressBar.visibility = View.VISIBLE
        }else {
            mProgressBar.visibility = View.GONE
        }
    }

    private fun setupPermissions() {
        manager = getSystemService( Context.LOCATION_SERVICE ) as LocationManager
        if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if(Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ApplicationConstants.REQUEST_CODE_GPS)
            }else {
                locationService.getCurrentLocation()
                //placesViewModel.getCurrentLocation()
            }
        }else {
            if(Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ApplicationConstants.REQUEST_CODE_GPS)
            }else {
                locationService.getCurrentLocation()
               //placesViewModel.getCurrentLocation()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode) {
            ApplicationConstants.REQUEST_CODE_GPS -> {
                if(grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    ApplicationUtility.showSnack(getString(R.string.allow_gps), rootLayout, getString(R.string.ok))
                }else {
                    locationService.getCurrentLocation()
                    //placesViewModel.getCurrentLocation()
                }
            }
        }
    }

    override fun onLocationFetched(location: Location?) {
        this.getPlaces(location?.latitude, location?.longitude)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            ApplicationConstants.REQUEST_CHECK_SETTINGS_GPS -> {
                locationService.onActivityResult(requestCode, resultCode, data)
                //this.placesViewModel.onActivityResult(requestCode, resultCode, data)
            }
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        mMap = p0!!
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
        mMap.uiSettings?.isCompassEnabled = true
        mMap.uiSettings?.isZoomGesturesEnabled = true
        locationService = LocationService(application)
        locationService.setLocationCallback(this)
        this.setupPermissions()
    }

    override fun checkGPS(
        googleApiClient: GoogleApiClient,
        locationSetting: LocationSettingsRequest.Builder,
        locationRequest: LocationRequest,
        locationListener: LocationListener
    ) {
        val result: PendingResult<LocationSettingsResult> = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, locationSetting.build())
        result.setResultCallback { result ->
            val status = result.status
            when (status.statusCode) {
                LocationSettingsStatusCodes.SUCCESS -> {
                    // All location settings are satisfied.
                    // You can initialize location requests here.
                    val permissionLocation = ContextCompat
                        .checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                    if (permissionLocation == PackageManager.PERMISSION_GRANTED) {
                        LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, locationListener)
                    }
                }
                LocationSettingsStatusCodes.RESOLUTION_REQUIRED ->
                    // Location settings are not satisfied.
                    // But could be fixed by showing the user a dialog.
                    try {
                        // Show the dialog by calling startResolutionForResult(),
                        // and check the result in onActivityResult().
                        // Ask to turn on GPS automatically
                        status.startResolutionForResult(this,
                            ApplicationConstants.REQUEST_CHECK_SETTINGS_GPS)
                    } catch (e: Exception) {
                        // Ignore the error.
                    }
                LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {}
            }// Location settings are not satisfied. However, we have no way to fix the
            // settings so we won't show the dialog.
            //finish();
        }
    }
}