package com.rubenquadros.yourplaces.di.module

import android.app.Application
import com.rubenquadros.yourplaces.data.local.database.PlacesDatabase
import dagger.Module
import dagger.Provides

@Module
class DbModule(private val application: Application) {

}