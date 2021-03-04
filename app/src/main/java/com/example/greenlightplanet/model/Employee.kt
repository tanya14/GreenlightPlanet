package com.example.greenlightplanet.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class Employee {
    @SerializedName("area")
    @Expose
    var area: String? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("territory")
    @Expose
    var territory: String? = null
}