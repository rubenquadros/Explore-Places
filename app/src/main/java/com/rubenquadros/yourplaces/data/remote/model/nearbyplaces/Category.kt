package com.rubenquadros.yourplaces.data.remote.model.nearbyplaces

import com.google.gson.annotations.SerializedName

class Category {

    @SerializedName("icon")
    var icon: Icon? = null
    @SerializedName("id")
    var id: String? = null
    @SerializedName("name")
    var name: String? = null
    @SerializedName("pluralName")
    var pluralName: String? = null
    @SerializedName("primary")
    var primary: Boolean? = null
    @SerializedName("shortName")
    var shortName: String? = null

}
