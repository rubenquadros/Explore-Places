package com.rubenquadros.yourplaces.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.configureDagger()
    }

    private fun configureDagger() =(this.application as BaseApplication).appComponent.inject(this)
}