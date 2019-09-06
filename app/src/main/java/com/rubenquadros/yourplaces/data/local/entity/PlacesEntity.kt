package com.rubenquadros.yourplaces.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "location_data")
data class PlacesEntity(
    @PrimaryKey
    var id: Int?,

    @ColumnInfo(name = "latitude")
    var latitude: Double?,

    @ColumnInfo(name = "longitude")
    var longitude: Double?,

    @ColumnInfo(name = "name")
    var name: String?,

    @ColumnInfo(name = "category")
    var category: String?,

    @ColumnInfo(name = "address")
    var address: String?
) {
}