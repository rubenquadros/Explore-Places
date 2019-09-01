package com.rubenquadros.yourplaces.data.remote.model.nearbyplaces

import com.google.gson.annotations.SerializedName

class HereNow {

    @SerializedName("count")
    var count: Long? = null
    @SerializedName("groups")
    var groups: List<Group>? = null
    @SerializedName("summary")
    var summary: String? = null

}
