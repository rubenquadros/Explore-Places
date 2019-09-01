package com.rubenquadros.yourplaces.data.remote.model.searchplaces

import com.google.gson.annotations.SerializedName

class Geocode {

    @SerializedName("feature")
    var feature: Feature? = null
    @SerializedName("parents")
    var parents: List<Any>? = null
    @SerializedName("what")
    var what: String? = null
    @SerializedName("where")
    var where: String? = null

}
