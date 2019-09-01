package com.rubenquadros.yourplaces.data.remote.api

import com.rubenquadros.yourplaces.data.remote.model.nearbyplaces.PlacesResponse
import com.rubenquadros.yourplaces.data.remote.model.searchplaces.SearchPlacesResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PlacesApiService {
    @GET("venues/explore?client_id=P2SV0T31WRBJACVARCNW55U13YSPRL2TA5OCZCX2AZXEU31B&client_secret=HUC02LLJTGZG2BZZS1LVA4D4LATJCCUBZQI5LOBJJSW5BVWX&v=20180323&radius=1000")
    fun getNearbyPlaces(
        @Query("ll", encoded = true) ll: String
    ): Observable<PlacesResponse>

    @GET("venues/search?client_id=P2SV0T31WRBJACVARCNW55U13YSPRL2TA5OCZCX2AZXEU31B&client_secret=HUC02LLJTGZG2BZZS1LVA4D4LATJCCUBZQI5LOBJJSW5BVWX&v=20180323&near={place}&radius=1000")
    fun searchNearByPlaces(): Observable<SearchPlacesResponse>
}