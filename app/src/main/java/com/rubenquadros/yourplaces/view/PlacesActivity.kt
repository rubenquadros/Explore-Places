package com.rubenquadros.yourplaces.view

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import butterknife.BindView
import butterknife.ButterKnife
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.rubenquadros.yourplaces.R
import com.rubenquadros.yourplaces.base.BaseActivity
import com.rubenquadros.yourplaces.databinding.ActivityPlacesBinding
import com.rubenquadros.yourplaces.utils.ApplicationConstants
import com.rubenquadros.yourplaces.utils.ApplicationUtility
import com.rubenquadros.yourplaces.viewmodel.PlacesViewModel

class PlacesActivity : BaseActivity(), OnMapReadyCallback {

    private lateinit var placesViewModel: PlacesViewModel
    private lateinit var manager: LocationManager

    @BindView(R.id.parent) lateinit var rootLayout: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.configureDesign()
    }

    private fun configureDesign() {
        this.setupDataBinding()
        this.setupStatusBar()
    }

    private fun setupDataBinding() {
        val activityPlacesBinding = DataBindingUtil.setContentView<ActivityPlacesBinding>(this, R.layout.activity_places)
        placesViewModel = ViewModelProviders.of(this, viewModelFactory)[PlacesViewModel::class.java]
        ButterKnife.bind(this)
        activityPlacesBinding.placesHandler = placesViewModel
    }

    private fun setupStatusBar() {
        rootLayout.systemUiVisibility = SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or SYSTEM_UI_FLAG_LAYOUT_STABLE
    }

    private fun setupPermissions() {
        manager = getSystemService( Context.LOCATION_SERVICE ) as LocationManager
        if(!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if(Build.VERSION.SDK_INT >= 23 &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ApplicationConstants.REQUEST_CODE_GPS)
            }
        }else {
            if(Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), ApplicationConstants.REQUEST_CODE_GPS)
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

                }
            }
        }
    }

    override fun onMapReady(p0: GoogleMap?) {
        p0?.mapType = GoogleMap.MAP_TYPE_NORMAL
        p0?.uiSettings?.isCompassEnabled = true
        p0?.uiSettings?.isZoomGesturesEnabled = true
        this.setupPermissions()
    }
}
