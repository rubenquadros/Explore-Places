package com.rubenquadros.yourplaces.data.remote.model.searchplaces

import com.google.gson.annotations.SerializedName

class HereNow {

    @SerializedName("count")
    var count: Long? = null
    @SerializedName("groups")
    var groups: List<Any>? = null
    @SerializedName("summary")
    var summary: String? = null

}
