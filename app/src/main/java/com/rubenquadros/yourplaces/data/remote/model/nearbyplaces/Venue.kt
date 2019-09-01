package com.rubenquadros.yourplaces.data.remote.model.nearbyplaces

import com.google.gson.annotations.SerializedName

class Venue {

    @SerializedName("beenHere")
    var beenHere: BeenHere? = null
    @SerializedName("categories")
    var categories: List<Category>? = null
    @SerializedName("contact")
    var contact: Contact? = null
    @SerializedName("hereNow")
    var hereNow: HereNow? = null
    @SerializedName("id")
    var id: String? = null
    @SerializedName("location")
    var location: Location? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("photos")
    var photos: Photos? = null
    @SerializedName("stats")
    var stats: Stats? = null
    @SerializedName("verified")
    var verified: Boolean? = null

}
