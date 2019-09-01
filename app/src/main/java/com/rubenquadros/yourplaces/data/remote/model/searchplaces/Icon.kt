package com.rubenquadros.yourplaces.data.remote.model.searchplaces

import com.google.gson.annotations.SerializedName

class Icon {

    @SerializedName("prefix")
    var prefix: String? = null
    @SerializedName("suffix")
    var suffix: String? = null

}
