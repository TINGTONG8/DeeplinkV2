package com.example.myapplication.data

import com.google.gson.annotations.SerializedName

//Token that Prince Response
data class Data (
    @SerializedName("token") var token: String?  = null,
)
