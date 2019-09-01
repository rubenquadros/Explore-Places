package com.rubenquadros.yourplaces.di

import android.app.Application
import androidx.room.Room
import com.rubenquadros.yourplaces.data.local.dao.PlacesDAO
import com.rubenquadros.yourplaces.data.local.database.PlacesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class TestDbModule(private val application: Application) {
    @Provides
    @Singleton
    fun provideDatabase(): PlacesDatabase {
        return Room.inMemoryDatabaseBuilder(application, PlacesDatabase::class.java)
            .allowMainThreadQueries().fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun providePlacesDAO(placesDatabase: PlacesDatabase): PlacesDAO {
        return placesDatabase.placesDAO()
    }
}