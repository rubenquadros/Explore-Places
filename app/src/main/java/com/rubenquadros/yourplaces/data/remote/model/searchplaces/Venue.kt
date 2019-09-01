package com.rubenquadros.yourplaces.data.remote.model.searchplaces

import com.google.gson.annotations.SerializedName

class Venue {

    @SerializedName("beenHere")
    var beenHere: BeenHere? = null
    @SerializedName("categories")
    var categories: List<Category>? = null
    @SerializedName("contact")
    var contact: Contact? = null
    @SerializedName("hasPerk")
    var hasPerk: Boolean? = null
    @SerializedName("hereNow")
    var hereNow: HereNow? = null
    @SerializedName("id")
    var id: String? = null
    @SerializedName("location")
    var location: Location? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("referralId")
    var referralId: String? = null
    @SerializedName("stats")
    var stats: Stats? = null
    @SerializedName("venueChains")
    var venueChains: List<Any>? = null
    @SerializedName("verified")
    var verified: Boolean? = null

}
