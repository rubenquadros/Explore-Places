package com.rubenquadros.yourplaces.data.remote.model.nearbyplaces

import com.google.gson.annotations.SerializedName

class PlacesResponse {

    @SerializedName("meta")
    var meta: Meta? = null
    @SerializedName("response")
    var response: Response? = null

}
