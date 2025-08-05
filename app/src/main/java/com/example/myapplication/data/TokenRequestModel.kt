package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

//TokenRequestModel that Prince Requests
data class TokenRequestModel(
    @SerializedName("transOrderNo")  var transOrderNo  : String? = null,
    @SerializedName("amt")           var amt           : Int? = null,
    @SerializedName("currency")      var currency      : String? = null,
    @SerializedName("remark")        var remark        : String? = null,
    @SerializedName("notifyUrl")     var notifyUrl     : String? = null,
    @SerializedName("expireMinutes") var expireMinutes : Int? = null,
)