package com.rubenquadros.yourplaces.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rubenquadros.yourplaces.data.local.dao.PlacesDAO
import com.rubenquadros.yourplaces.data.local.entity.PlacesEntity

@Database(entities = [PlacesEntity::class], version = 1)
abstract class PlacesDatabase: RoomDatabase() {

    abstract fun placesDAO(): PlacesDAO

}