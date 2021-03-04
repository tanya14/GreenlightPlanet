package com.example.greenlightplanet.model

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class ResponseData {
    @SerializedName("ResponseStatus")
    @Expose
    var responseStatus: Int? = null

    @SerializedName("ResponseData")
    @Expose
    var responseData: Performance? = null

    @SerializedName("Success")
    @Expose
    var success: Boolean? = null
}