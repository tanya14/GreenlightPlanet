package com.example.greenlightplanet.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Zone {
    @SerializedName("zone")
    @Expose
    var zone: String? = null

    @SerializedName("territory")
    @Expose
    var territory: String? = null
}