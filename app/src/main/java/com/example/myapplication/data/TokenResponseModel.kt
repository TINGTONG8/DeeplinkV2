package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

//TokenResponseModel that Prince Response
data class TokenResponseModel(
    @SerializedName("data")    var data    : Data?    = Data(),
    @SerializedName("status")  var status  : String?  = null,
    @SerializedName("message") var message : String?  = null,
    @SerializedName("success") var success : Boolean? = null,
    @SerializedName("code")    var code    : Int?     = null,
)