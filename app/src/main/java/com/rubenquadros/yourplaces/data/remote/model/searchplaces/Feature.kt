package com.rubenquadros.yourplaces.data.remote.model.searchplaces

import com.google.gson.annotations.SerializedName

class Feature {

    @SerializedName("cc")
    var cc: String? = null
    @SerializedName("displayName")
    var displayName: String? = null
    @SerializedName("geometry")
    var geometry: Geometry? = null
    @SerializedName("highlightedName")
    var highlightedName: String? = null
    @SerializedName("id")
    var id: String? = null
    @SerializedName("longId")
    var longId: String? = null
    @SerializedName("matchedName")
    var matchedName: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("woeType")
    var woeType: Long? = null

}
