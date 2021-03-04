package com.example.greenlightplanet.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Region {
    @SerializedName("region")
    @Expose
    var region: String? = null

    @SerializedName("territory")
    @Expose
    var territory: String? = null
}