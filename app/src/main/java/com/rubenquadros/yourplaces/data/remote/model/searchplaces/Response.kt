package com.rubenquadros.yourplaces.data.remote.model.searchplaces

import com.google.gson.annotations.SerializedName

class Response {

    @SerializedName("confident")
    var confident: Boolean? = null
    @SerializedName("geocode")
    var geocode: Geocode? = null
    @SerializedName("venues")
    var venues: List<Venue>? = null

}
