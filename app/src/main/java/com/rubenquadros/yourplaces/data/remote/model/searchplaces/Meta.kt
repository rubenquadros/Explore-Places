package com.rubenquadros.yourplaces.data.remote.model.searchplaces

import com.google.gson.annotations.SerializedName

class Meta {

    @SerializedName("code")
    var code: Long? = null
    @SerializedName("requestId")
    var requestId: String? = null

}
