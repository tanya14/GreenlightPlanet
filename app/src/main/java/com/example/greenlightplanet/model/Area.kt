package com.example.greenlightplanet.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Area {
    @SerializedName("area")
    @Expose
    var area: String? = null

    @SerializedName("territory")
    @Expose
    var territory: String? = null
}