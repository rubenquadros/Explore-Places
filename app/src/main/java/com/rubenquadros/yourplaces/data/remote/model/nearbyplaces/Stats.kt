package com.rubenquadros.yourplaces.data.remote.model.nearbyplaces

import com.google.gson.annotations.SerializedName

class Stats {

    @SerializedName("checkinsCount")
    var checkinsCount: Long? = null
    @SerializedName("tipCount")
    var tipCount: Long? = null
    @SerializedName("usersCount")
    var usersCount: Long? = null
    @SerializedName("visitsCount")
    var visitsCount: Long? = null

}
