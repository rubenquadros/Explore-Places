package com.rubenquadros.yourplaces.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.rubenquadros.yourplaces.R
import com.rubenquadros.yourplaces.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.startSplash()
    }

    private fun startSplash() {
        Handler().postDelayed({
            startActivity(Intent(this, PlacesActivity::class.java))
            finish()
        }, 2000)
    }
}
