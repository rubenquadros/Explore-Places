package com.rubenquadros.yourplaces.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_data")
data class PlacesEntity(
    @PrimaryKey
    var id: Int?
) {
}