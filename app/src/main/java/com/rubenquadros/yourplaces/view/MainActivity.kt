package com.rubenquadros.yourplaces.view

import android.os.Bundle
import com.rubenquadros.yourplaces.R
import com.rubenquadros.yourplaces.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
