package com.example.greenlightplanet.model

import android.graphics.Region
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Area {
    @SerializedName("area")
    @Expose
    var region: String? = null

    @SerializedName("territory")
    @Expose
    var territory: String? = null
}