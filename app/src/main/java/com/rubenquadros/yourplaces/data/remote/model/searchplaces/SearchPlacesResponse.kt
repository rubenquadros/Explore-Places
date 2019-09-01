package com.rubenquadros.yourplaces.data.remote.model.searchplaces

import com.google.gson.annotations.SerializedName

class SearchPlacesResponse {

    @SerializedName("meta")
    var meta: Meta? = null
    @SerializedName("response")
    var response: Response? = null

}
