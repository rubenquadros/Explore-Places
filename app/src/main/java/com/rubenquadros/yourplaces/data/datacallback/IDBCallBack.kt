package com.rubenquadros.yourplaces.data.datacallback

import com.rubenquadros.yourplaces.data.local.entity.PlacesEntity

interface IDBCallBack {
    fun onQueryExecuted(placesData: List<PlacesEntity>)
}