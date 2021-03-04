package com.example.greenlightplanet.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Country {
    @SerializedName("country")
    @Expose
    var country: String? = null

    @SerializedName("territory")
    @Expose
    var territory: String? = null
}