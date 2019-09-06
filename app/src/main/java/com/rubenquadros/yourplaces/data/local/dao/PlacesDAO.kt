package com.rubenquadros.yourplaces.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.rubenquadros.yourplaces.data.local.entity.PlacesEntity

@Dao
interface PlacesDAO {

    @Insert
    fun insertAll(data: ArrayList<PlacesEntity>)

    @Query("SELECT * FROM location_data")
    fun getPlaces(): List<PlacesEntity>

    @Query("DELETE FROM location_data")
    fun deleteAll()

}