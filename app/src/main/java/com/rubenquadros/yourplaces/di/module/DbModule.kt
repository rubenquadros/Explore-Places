package com.rubenquadros.yourplaces.di.module

import android.app.Application
import androidx.room.Room
import com.rubenquadros.yourplaces.data.local.dao.PlacesDAO
import com.rubenquadros.yourplaces.data.local.database.PlacesDatabase
import dagger.Module
import dagger.Provides

@Module
class DbModule(private val application: Application) {
    @Provides
    fun providePlacesDatabase(): PlacesDatabase {
        return Room.databaseBuilder(application, PlacesDatabase::class.java, "Places.db")
            .fallbackToDestructiveMigration().build()
    }

    @Provides
    fun providePlacesDAO(placesDatabase: PlacesDatabase): PlacesDAO {
        return placesDatabase.placesDAO()
    }
}