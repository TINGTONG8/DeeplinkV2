package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

//TokenResponseModel that Prince Response
data class TokenResponseStatusModel(
    @SerializedName("code") var code: Int?       = null,
    @SerializedName("data") var data: DataStatus = DataStatus(),
)