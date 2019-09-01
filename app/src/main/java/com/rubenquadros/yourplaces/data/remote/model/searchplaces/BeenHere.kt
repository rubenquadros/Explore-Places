package com.rubenquadros.yourplaces.data.remote.model.searchplaces

import com.google.gson.annotations.SerializedName

class BeenHere {

    @SerializedName("count")
    var count: Long? = null
    @SerializedName("lastCheckinExpiredAt")
    var lastCheckinExpiredAt: Long? = null
    @SerializedName("marked")
    var marked: Boolean? = null
    @SerializedName("unconfirmedCount")
    var unconfirmedCount: Long? = null

}
